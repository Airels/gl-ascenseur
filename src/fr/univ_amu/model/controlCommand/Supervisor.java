package fr.univ_amu.model.controlCommand;

import elevator.IElevator;
import elevator.IPanel;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.model.controlCommand.schedulers.Scheduler;
import fr.univ_amu.model.controlCommand.schedulers.SchedulerBuilder;
import fr.univ_amu.model.exceptions.UnhandledStrategyException;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.ExceptionHandler;

/**
 * The main system of the elevator, who controls everything and takes action decided by schedulers
 *
 * @author VIZCAINO Yohan
 */
public class Supervisor implements Runnable {

    /**
     *
     */
    private int currentLevel;
    /**
     *
     */
    private Movement currentTravelDirection;
    /**
     *
     */
    private boolean isSystemHalted;
    /**
     *
     */
    private Request currentRequest;
    /**
     *
     */
    private Scheduler scheduler;
    /**
     *
     */
    private PanelManager panelManager;
    /**
     *
     */
    private IElevator elevator;

    private boolean requestedDisableEmergency;

    /**
     * Default constructor
     */
    public Supervisor(IPanel panel, IElevator elevator) {
        try {
            scheduler = SchedulerBuilder.build(Configuration.REQUEST_SATISTACTION_STRATEGY);
        } catch (UnhandledStrategyException e) {
            ExceptionHandler.showAndExit(e);
        }

        this.isSystemHalted = false;
        this.panelManager = new PanelManager(panel,this);
        this.elevator = elevator;
        currentLevel = 0;
        currentTravelDirection = Movement.IDLE;
        requestedDisableEmergency = false;
    }

    /**
     * Unit of computation time of Supervisor
     */
    public void tick() {
        if (requestedDisableEmergency)
            disableEmergency();

        if (isSystemHalted) return;

        if (elevator.getAndResetStageSensor()) {
            if (requestedDisableEmergency) {
                requestedDisableEmergency = false;
                return;
            }

            currentLevel += (currentTravelDirection == Movement.UP) ? 1 : -1;

            int requestedLevel = requestedLevel();
            if (requestedLevel == currentLevel) {
                requestSatisfied();
                if (scheduler.sortRequests(currentTravelDirection)) {
                    executeRequest(scheduler.getCurrentRequest());
                }
                else {
                    currentTravelDirection = Movement.IDLE;
                    panelManager.updateMessage(currentTravelDirection, currentLevel);
                }
            }
            else if (currentRequest != null && Math.abs(currentLevel - requestedLevel()) == 1)
                elevator.stopNext();

            panelManager.updateMessage(currentTravelDirection, currentLevel);
        }

        if (panelManager.isEventAndReset()) {
            if (scheduler.sortRequests(currentTravelDirection)) {
                executeRequest(scheduler.getCurrentRequest());
            }
        }

        if (currentRequest != null)
            executeRequest(currentRequest);
    }

    /**
     * Add request to the queue (do not guarantee its execution)
     *
     * @param request
     */
    public void addRequest(Request request) {
        scheduler.addRequest(request);
    }

    /**
     * Define a request to be executed
     *
     * @param request
     */
    private void executeRequest(Request request) {
        currentRequest = request;

        int requestedLevel = requestedLevel();
        if (elevator.getState() == IElevator.State.STOP) {
            if (currentLevel - requestedLevel == 0) {
                elevator.openDoor();
                requestSatisfied();
            } else if (currentLevel - requestedLevel > 0) {
                currentTravelDirection = Movement.DOWN;
                elevator.down();
            } else {
                currentTravelDirection = Movement.UP;
                elevator.up();
            }

            if (currentRequest != null && Math.abs(currentLevel - requestedLevel) == 1)
                elevator.stopNext();
        } else {
            if (currentLevel - requestedLevel > 0) {
                currentTravelDirection = Movement.DOWN;
            } else {
                currentTravelDirection = Movement.UP;
            }
        }

        panelManager.updateMessage(currentTravelDirection, currentLevel);
    }

    private void requestSatisfied() {
        panelManager.requestSatisfied(currentRequest);
        scheduler.requestSatisfied(currentRequest);
        currentRequest = null;
    }

    /**
     * Enable the emergency elevator state. The elevator will remain locked until emergency state removal
     */
    public void enableEmergenry() {
        if (isSystemHalted) return;

        elevator.halt();
        isSystemHalted = true;
        currentRequest = null;
        scheduler.clearRequests();
        panelManager.sendEmergencyMessage();
    }

    /**
     * Ask to supervisor to disable emergency state
     */
    public void requestDisableEmergency() {
        if (isSystemHalted)
            requestedDisableEmergency = true;
    }

    /**
     * Disable emergency mode
     */
    private void disableEmergency() {
        if (!isSystemHalted) return;

        elevator.reset();
        panelManager.sendResetMessage();
        while (elevator.getState() == IElevator.State.RESET) {
            try {
                Thread.sleep(Configuration.SUPERVISOR_TICK_TIME);
            } catch (InterruptedException ignored) {}
        }
        panelManager.updateMessage(Movement.IDLE, 0);
        currentTravelDirection = Movement.IDLE;
        currentLevel = 0;
        isSystemHalted = false;
    }

    /**
     * Know if system is halted
     * @return boolean true if halted, false otherwise
     */
    public boolean isSystemHalted() {
        return isSystemHalted;
    }

    private int requestedLevel() {
        return requestedLevel(currentRequest);
    }

    private int requestedLevel(Request request) {
        return switch (request.getRequestOrigin()) {
            case INSIDE -> request.getTargetLevel();
            case OUTSIDE -> request.getSourceLevel();
        };
    }

    @Override
    public void run() {
        new Thread(panelManager).start();

        panelManager.updateMessage(Movement.IDLE, 0);

        while (!Thread.interrupted()) {
            tick();
            try {
                Thread.sleep(Configuration.SUPERVISOR_TICK_TIME);
            } catch (InterruptedException ignored) {}
        }
    }
}
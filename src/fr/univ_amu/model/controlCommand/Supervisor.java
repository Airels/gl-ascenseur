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
    private final PanelManager panelManager;
    /**
     *
     */
    private final IElevator elevator;
    /**
     *
     */
    private int currentLevel;
    /**
     *
     */
    private Movement currentMovement;
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
    private boolean requestedDisableEmergency;

    /**
     * Default constructor
     */
    public Supervisor(IPanel panel, IElevator elevator) {
        try {
            scheduler = SchedulerBuilder.build(Configuration.REQUEST_SATISFACTIONS_STRATEGY);
        } catch (UnhandledStrategyException e) {
            ExceptionHandler.showAndExit(e);
        }

        this.isSystemHalted = false;
        this.panelManager = new PanelManager(panel, this);
        this.elevator = elevator;
        currentLevel = 0;
        currentMovement = Movement.IDLE;
        requestedDisableEmergency = false;
    }

    /**
     * Unit of computation time of Supervisor
     */
    public void tick() {
        if (requestedDisableEmergency)
            disableEmergency();

        if (isSystemHalted) return;

        if (elevator.getAndResetStageSensor())
            handleStageSensorEvent();

        if (panelManager.isEventAndReset() && scheduler.getCurrentRequest() != null)
            executeRequest(scheduler.getCurrentRequest());

        if (currentRequest == null && scheduler.getCurrentRequest() != null)
            executeRequest(scheduler.getCurrentRequest());

        if (currentRequest != null && elevator.getState() == IElevator.State.STOP)
            executeRequest(currentRequest);

        if ((elevator.getState() == IElevator.State.UP || elevator.getState() == IElevator.State.DOWN)
                && Math.abs(currentLevel - currentRequest.getTargetLevel()) == 1) {
            elevator.stopNext();
        }

        panelManager.updateMessage(currentMovement, currentLevel);
    }

    /**
     * Handle an event level sensor
     */
    private void handleStageSensorEvent() {
        if (requestedDisableEmergency) {
            requestedDisableEmergency = false;
        }

        currentLevel += (currentMovement == Movement.UP) ? 1 : -1;

        if (currentRequest.getTargetLevel() == currentLevel) {
            requestSatisfied();
            if (scheduler.getCurrentRequest() == null) {
                currentMovement = Movement.IDLE;
                panelManager.updateMessage(currentMovement, currentLevel);
            } else
                executeRequest(scheduler.getCurrentRequest());
        }
    }

    /**
     * Add request to the queue (do not guarantee its execution), and returns if it was added
     *
     * @param request request to add
     * @return if request was added
     */
    public boolean addRequest(Request request) {
        int level = currentLevel;

        if (request.getTargetLevel() == currentLevel && elevator.getState() == IElevator.State.STOP) {
            elevator.openDoor();
            return false;
        }

        scheduler.addRequest(request);
        scheduler.sortRequests(level, currentMovement);
        return true;
    }

    /**
     * Define a request to be executed
     *
     * @param request request to execute
     */
    private void executeRequest(Request request) {
        currentRequest = request;

        int requestedLevel = currentRequest.getTargetLevel();
        if (currentLevel - requestedLevel == 0) {
            switch (elevator.getState()) {
                case STOP:
                    elevator.openDoor();
                case DOOR:
                    requestSatisfied();
            }
        } else if (currentLevel - requestedLevel > 0) {
            sendMovementCommand(Movement.DOWN);
        } else {
            sendMovementCommand(Movement.UP);
        }
    }

    /**
     * Called when current request was satisfied
     */
    private void requestSatisfied() {
        panelManager.levelSatisfied(currentRequest.getTargetLevel());
        scheduler.requestSatisfied();
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
     * Ask supervisor to disable emergency state
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
            } catch (InterruptedException ignored) {
            }
        }
        panelManager.updateMessage(Movement.IDLE, 0);
        currentMovement = Movement.IDLE;
        currentLevel = 0;
        isSystemHalted = false;
    }

    /**
     * Know if system is halted
     *
     * @return boolean true if halted, false otherwise
     */
    public boolean isSystemHalted() {
        return isSystemHalted;
    }

    private int requestedLevel(Request request) {
        return request.getTargetLevel();
    }

    /**
     * Get current level of the elevator
     *
     * @return int for the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void run() {
        new Thread(panelManager).start();

        panelManager.updateMessage(Movement.IDLE, 0);

        while (!Thread.interrupted()) {
            tick();
            try {
                Thread.sleep(Configuration.SUPERVISOR_TICK_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Send movement to elevator's engine and update current state
     *
     * @param movement movement to choose
     */
    public void sendMovementCommand(Movement movement) {
        currentMovement = movement;

        if (elevator.getState() == IElevator.State.STOP) {
            switch (movement) {
                case UP -> elevator.up();
                case DOWN -> elevator.down();
            }
        }
    }
}
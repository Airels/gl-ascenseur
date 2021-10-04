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
    private Movement travelDirection;
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
    }

    /**
     * Unit of computation time of Supervisor
     */
    public void tick() {
        // TODO implement here
    }

    /**
     * Add request to the queue (do not guarantee its execution)
     *
     * @param request
     */
    public void addRequest(Request request) {
        if (!isSystemHalted)
            scheduler.addRequest(request);
    }

    /**
     *
     */
    private void sortRequests() {
        if (scheduler.sortRequests(travelDirection))
            executeRequest(scheduler.getAndResetCurrentRequest());
    }

    /**
     * Define a request to be executed
     *
     * @param request
     */
    private void executeRequest(Request request) {
        currentRequest = request;
    }

    /**
     * Enable the emergency elevator state. The elevator will remain locked until emergency state removal
     */
    public void enableEmergenry() {
        isSystemHalted = true;
        scheduler.clearRequests();
    }

    /**
     * Disable the emergency elevator state.
     */
    public void disableEmergency() {
        isSystemHalted = false;
    }

    /**
     * Know if system is halted
     * @return boolean true if halted, false otherwise
     */
    public boolean isSystemHalted() {
        return isSystemHalted;
    }

    @Override
    public void run() {
        new Thread(panelManager).start();

        while (!Thread.interrupted()) {
            tick();
            try {
                Thread.sleep(Configuration.SUPERVISOR_TICK_TIME);
            } catch (InterruptedException ignored) {}
        }
    }
}
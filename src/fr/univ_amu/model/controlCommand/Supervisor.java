package fr.univ_amu.model.controlCommand;

import elevator.IElevator;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.model.controlCommand.schedulers.Scheduler;

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
    private boolean systemHalted;
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
    public Supervisor() {
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
        // TODO implement here
    }

    /**
     *
     */
    private void sortRequests() {
        // TODO implement here
    }

    /**
     * Define a request to be executed
     *
     * @param request
     */
    private void executeRequest(Request request) {
        // TODO implement here
    }

    /**
     * Toggle the emergency elevator state. The elevator will remain locked until next call
     */
    public void toggleEmergency() {
        // TODO implement here
    }

    @Override
    public void run() {

    }
}
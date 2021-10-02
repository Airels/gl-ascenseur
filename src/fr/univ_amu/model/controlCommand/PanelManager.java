package fr.univ_amu.model.controlCommand;

import elevator.IPanel;
import fr.univ_amu.model.controlCommand.schedulers.Scheduler;

/**
 * Panel manager who will listen to panel interface to catch events and transforms them into Requests independently from Supervisor
 *
 * @author VIZCAINO Yohan
 */
public class PanelManager implements Runnable {

    /**
     *
     */
    private IPanel panel;
    /**
     *
     */
    private Scheduler scheduler;
    /**
     *
     */
    private Supervisor supervisor;

    /**
     * Default constructor
     */
    public PanelManager() {
    }

    /**
     * Infinite loop who process all user interactions
     */
    public void tick() {
        // TODO implement here
    }

    /**
     * Called by the Supervisor, return a boolean to tell if at least an action was caught and reset it
     *
     * @return
     */
    public boolean isEventAndReset() {
        // TODO implement here
        return false;
    }

    @Override
    public void run() {

    }
}
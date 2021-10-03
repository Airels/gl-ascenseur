package fr.univ_amu.model.controlCommand;

import elevator.IPanel;
import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Request;
import fr.univ_amu.model.controlCommand.schedulers.Scheduler;
import fr.univ_amu.utils.Configuration;

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
    private Supervisor supervisor;

    private boolean isEventByUser;

    /**
     * Default constructor
     */
    public PanelManager(IPanel iPanel, Supervisor supervisor) {
        this.supervisor = supervisor;
        panel = iPanel;
        isEventByUser = false;
    }

    /**
     * Called by the Supervisor, return a boolean to tell if at least an action was caught and reset it
     *
     * @return
     */
    public boolean isEventAndReset() {
        if (isEventByUser) {
            isEventByUser = false;
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            tick();
            try {
                Thread.sleep(Configuration.PANEL_MANAGER_TICK_TIME);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * Infinite loop who process all user interactions
     */
    public void tick() {
        if (panel.getAndResetStopButton()) {
            supervisor.enableEmergenry();
            return;
        }

        if (panel.getAndResetInitButton())
            supervisor.disableEmergency();

        if (panel.getAndResetButtonsSensor()) {
            for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                if (panel.getAndResetFloorButton(i)) {
                    supervisor.addRequest(new Request(i));
                    panel.setFloorLight(i, true);
                }

                if (panel.getAndResetDownButton(i)) {
                    supervisor.addRequest(new Request(Direction.DOWN, i));
                    panel.setDownLight(i, true);
                }

                if (panel.getAndResetUpButton(i)) {
                    supervisor.addRequest(new Request(Direction.UP, i));
                    panel.setUpLight(i, true);
                }
            }
        }
    }
}
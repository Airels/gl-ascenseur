package fr.univ_amu.model.controlCommand;

import elevator.IPanel;
import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.TextTransformation;

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

            for (int i = 0; i < Configuration.MAX_LEVEL; i++) {
                panel.setFloorLight(i, false);
                panel.setUpLight(i, false);
                panel.setDownLight(i, false);
            }

            if (panel.getAndResetButtonsSensor()) {
                for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                    panel.getAndResetFloorButton(i);
                    panel.getAndResetUpButton(i);
                    panel.getAndResetDownButton(i);
                }
            }

            return;
        }

        if (panel.getAndResetInitButton())
            supervisor.requestDisableEmergency();

        if (panel.getAndResetButtonsSensor()) {
            for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                if (panel.getAndResetFloorButton(i)) {
                    if (!supervisor.isSystemHalted()) {
                        supervisor.addRequest(new Request(i));
                        isEventByUser = true;
                        panel.setFloorLight(i, true);
                    }
                }

                if (panel.getAndResetDownButton(i)) {
                    if (!supervisor.isSystemHalted()) {
                        supervisor.addRequest(new Request(Direction.DOWN, i));
                        isEventByUser = true;
                        panel.setDownLight(i, true);
                    }
                }

                if (panel.getAndResetUpButton(i)) {
                    if (!supervisor.isSystemHalted()) {
                        supervisor.addRequest(new Request(Direction.UP, i));
                        isEventByUser = true;
                        panel.setUpLight(i, true);
                    }
                }
            }
        }
    }

    /**
     * Allows to switch off buttons who triggered this request
     * @param request satisfied request
     */
    public void requestSatisfied(Request request) {
        switch (request.getRequestOrigin()) {
            case INSIDE -> panel.setFloorLight(request.getTargetLevel(), false);
            case OUTSIDE -> {
                switch (request.getDirection()) {
                    case UP -> panel.setUpLight(request.getSourceLevel(), false);
                    case DOWN -> panel.setDownLight(request.getSourceLevel(), false);
                }
            }
        }
    }

    /**
     * Allows to update the internal view elevator to informs user about elevator's state
     */
    public void updateMessage(Movement currentMovement, int currentLevel) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (currentMovement) {
            case UP -> stringBuilder.append("↑");
            case DOWN -> stringBuilder.append("↓");
            case IDLE -> stringBuilder.append("-");
        };

        stringBuilder.append(TextTransformation.intTwoDigits(currentLevel));

        panel.setMessage(stringBuilder.toString());
    }

    /**
     * Shows to user an emergency message when emergency is triggered
     */
    public void sendEmergencyMessage() {
        panel.setMessage("HS!");
    }

    /**
     * Shows a reset message to user when elevator is reset
     */
    public void sendResetMessage() {
        panel.setMessage("RST");
    }
}
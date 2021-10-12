package fr.univ_amu.controller;

import elevator.IPanelSimulator;
import fr.univ_amu.model.ButtonType;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.view.InternalPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller of GUI.
 * Handle buttons events when clicked on internal panel
 *
 * @author VIZCAINO Yohan
 */
public class InternalPanelController implements ActionListener {

    private int level;
    private ButtonType buttonType;
    private IPanelSimulator panelSimulator;
    private InternalPanelView view;

    /**
     * Default constructor, for classical call buttons
     *
     * @param panelSimulator inside and outside panel simulation
     * @param level          level to handle
     */
    public InternalPanelController(IPanelSimulator panelSimulator, int level) {
        this.panelSimulator = panelSimulator;
        this.level = level;
    }

    /**
     * Second constructor, for special buttons
     *
     * @param view associated
     * @param panelSimulator inside and outside panel simulation
     * @param buttonType     button type to handle
     * @see ButtonType to see all special buttons
     */
    public InternalPanelController(InternalPanelView view, IPanelSimulator panelSimulator, ButtonType buttonType) {
        this.panelSimulator = panelSimulator;
        level = -1;
        this.buttonType = buttonType;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (level >= 0)
            panelSimulator.pressFloorButton(level);
        else {
            switch (buttonType) {
                case BREAK -> {
                    panelSimulator.pressStopButton();
                    view.illuminateButton(Configuration.MAX_LEVEL + 1);
                }
                case RESET -> {
                    panelSimulator.pressInitButton();
                    view.switchOffButton(Configuration.MAX_LEVEL + 1);
                }
            }
        }
    }
}


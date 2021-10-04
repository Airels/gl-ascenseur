package fr.univ_amu.controller;

import elevator.IPanelSimulator;
import fr.univ_amu.model.ButtonType;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller of GUI.
 * Handle buttons events when clicked on internal panel
 * @author VIZCAINO Yohan
 */
public class InternalPanelController implements ActionListener {

    private int level;
    private ButtonType buttonType;
    private IPanelSimulator panelSimulator;

    public InternalPanelController(IPanelSimulator panelSimulator, int level) {
        this.panelSimulator = panelSimulator;
        this.level = level;
    }

    public InternalPanelController(IPanelSimulator panelSimulator, ButtonType buttonType) {
        this.panelSimulator = panelSimulator;
        level = -1;
        this.buttonType = buttonType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (level >= 0)
            panelSimulator.pressFloorButton(level);
        else {
            switch (buttonType) {
                case BREAK -> {
                    panelSimulator.pressStopButton();
                    InternalPanelView.getInstance().illuminateButton(Configuration.MAX_LEVEL+1);
                }
                case RESET -> {
                    panelSimulator.pressInitButton();
                    InternalPanelView.getInstance().switchOffButton(Configuration.MAX_LEVEL+1);
                }
            }
        }
    }
}


package fr.univ_amu.controller;

import elevator.IPanelSimulator;
import fr.univ_amu.model.Direction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller of GUI.
 * Handle buttons events when clicked on internal panel
 * @author VIZCAINO Yohan
 */
public class ExternalPanelController implements ActionListener {

    private int level;
    private Direction direction;
    private IPanelSimulator panelSimulator;

    public ExternalPanelController(IPanelSimulator panelSimulator, int level, Direction direction) {
        this.level = level;
        this.direction = direction;
        this.panelSimulator = panelSimulator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (direction) {
            case UP -> panelSimulator.pressUpButton(level);
            case DOWN -> panelSimulator.pressDownButton(level);
        }
    }
}

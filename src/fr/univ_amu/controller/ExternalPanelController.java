package fr.univ_amu.controller;

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

    public ExternalPanelController(int level, Direction direction) {
        this.level = level;
        this.direction = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(level + " " + direction);
    }
}

package fr.univ_amu.controller;

import fr.univ_amu.model.ButtonType;

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

    public InternalPanelController(int level) {
        this.level = level;
    }

    public InternalPanelController(ButtonType buttonType) {
        level = -1;
        this.buttonType = buttonType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (level >= 0)
            System.out.println("internal " + level);
        else
            System.out.println(buttonType);
    }
}


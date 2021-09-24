package fr.univ_amu.view;

import static fr.univ_amu.utils.Configuration.MAX_LEVEL;

/**
 * Visual representation of the internal panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class InternalPanelView {

    /**
     * 
     */
    private char[] screenText;

    /**
     * Arrays of boolean, when true, needs to illuminate button. (MAX+1 for emergency button)
     */
    private int[] illuminatedLevelButtons;


    /**
     * Default constructor
     */
    public InternalPanelView() {
        illuminatedLevelButtons = new int[MAX_LEVEL+3];
    }

    /**
     * Update screen text
     * @param message
     */
    public void setScreenText(String message) {
        // TODO implement here
    }

    /**
     * @param level
     */
    public void illuminateButton(int level) {
        // TODO implement here
    }

}
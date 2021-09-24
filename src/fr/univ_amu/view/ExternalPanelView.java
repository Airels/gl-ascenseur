package fr.univ_amu.view;

import java.util.*;

/**
 * Visual representation of external panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class ExternalPanelView {

    /**
     * Default constructor
     */
    public ExternalPanelView() {
    }

    /**
     * Level of where panel is located
     */
    private int level;

    /**
     * 
     */
    private boolean illuminatedUpButton;

    /**
     * 
     */
    private boolean illuminatedDownButton;


    /**
     * Turn on specific light of panel
     * @param direction
     */
    public void setLight(Direction direction) {
        // TODO implement here
    }

    /**
     * Turn off both lights of the panel
     */
    public void resetLights() {
        // TODO implement here
    }

}
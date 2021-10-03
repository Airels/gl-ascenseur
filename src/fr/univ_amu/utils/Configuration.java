package fr.univ_amu.utils;

import java.util.*;

/**
 * Configuration class used to easily configure elevator and its behaviour
 * @author VIZCAINO Yohan
 */
public class Configuration {

    /**
     * Maximum level that can reach elevator
     */
    public static final int MAX_LEVEL = 11;

    /**
     * Idle time (in seconds) between two requests
     */
    public static final int IDLE_DELAY = 5;

    /**
     * Strategy that main elevator control should use to process user requests
     */
    public static final SatisfactionStrategy REQUEST_SATISTACTION_STRATEGY = SatisfactionStrategy.DEFAULT;

    /**
     * FPS of the elevator simulation representation
     */
    public static final int FRAME_RATE_GUI = 4;


    /**
     * TODO
     */
    public static final int PANEL_MANAGER_TICK_TIME = 250;

    /**
     * TODO
     */
    public static final int SUPERVISOR_TICK_TIME = 125;
}
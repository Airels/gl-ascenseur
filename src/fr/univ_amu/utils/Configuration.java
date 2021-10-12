package fr.univ_amu.utils;

/**
 * Configuration class used to easily configure elevator and its behaviour
 *
 * @author VIZCAINO Yohan
 */
public class Configuration {

    /**
     * Maximum level that can reach elevator
     */
    public static final int MAX_LEVEL = 11;

    /**
     * Strategy that main elevator control should use to process user requests
     */
    public static final SatisfactionStrategy REQUEST_SATISFACTIONS_STRATEGY = SatisfactionStrategy.DEFAULT;

    /**
     * Time between each tick of the supervisor
     */
    public static final int SUPERVISOR_TICK_TIME = 125;

    /**
     * Time between each tick of the panel manager
     */
    public static final int PANEL_MANAGER_TICK_TIME = 250;

    /**
     * FPS of the elevator simulation representation
     */
    public static final int FRAME_RATE_GUI = 4;
}
package fr.univ_amu.model;

/**
 * Representation of Timestamp
 *
 * @author VIZCAINO Yohan
 */
public class Timestamp {

    private final long value;

    /**
     * Default constructor
     */
    public Timestamp() {
        value = System.currentTimeMillis();
    }

    /**
     * Get time in milliseconds of Timestamp
     *
     * @return milliseconds in long
     */
    public long getMilliseconds() {
        return value;
    }

    /**
     * Calculates differences between first and second timestamp
     *
     * @param timestamp timestamp to subtract
     * @return result of subtract
     */
    public long getDifference(Timestamp timestamp) {
        return this.getMilliseconds() - timestamp.getMilliseconds();
    }

}
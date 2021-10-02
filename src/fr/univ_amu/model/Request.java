package fr.univ_amu.model;

import java.util.*;

/**
 * User request representation, created when user calls elevator or order to go to another level. Works like a Promise
 * @author VIZCAINO Yohan
 */
public class Request {

    /**
     * Default constructor
     */
    public Request() {
    }

    /**
     * 
     */
    private RequestOrigin requestOrigin;

    /**
     * Requested direction (outside panel request)
     */
    private Direction direction;

    /**
     * Source request level (outside panel)
     */
    private int sourceLevel;

    /**
     * Level targeted by user (inside panel)
     */
    private int targetLevel;

    /**
     * Time when request was created
     */
    private Timestamp requestCreationTime;

    /**
     * 
     */
    private boolean priority;











    /**
     * Origin of the request (if is inside or outside of the elevator)
     * @return
     */
    public RequestOrigin getRequestOrigin() {
        // TODO implement here
        return null;
    }

    /**
     * Desired direction of the request (UP or DOWN), only for outside requests
     * @return
     */
    public Direction getDirection() {
        // TODO implement here
        return null;
    }

    /**
     * Source level of request
     * @return
     */
    public int getSourceLevel() {
        // TODO implement here
        return 0;
    }

    /**
     * Target level or request
     * @return
     */
    public int getTargetLevel() {
        // TODO implement here
        return 0;
    }

    /**
     * Gives timestamp when request was created
     * @return
     */
    public Timestamp getCreationTime() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public boolean isPriority() {
        // TODO implement here
        return false;
    }

}
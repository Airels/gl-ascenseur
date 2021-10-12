package fr.univ_amu.model;

/**
 * User request representation, created when user calls elevator or order to go to another level. Works like a Promise
 *
 * @author VIZCAINO Yohan
 */
public class Request implements Comparable<Request> {

    /**
     *
     */
    private RequestOrigin requestOrigin;
    /**
     * Requested direction (outside panel request)
     */
    private Direction direction;
    /**
     * Level targeted by user (inside panel)
     */
    private int targetLevel;
    /**
     * Time when request was created
     */
    private Timestamp requestCreationTime;

    /**
     * Default constructor
     */
    private Request(RequestOrigin requestOrigin, Direction direction, int targetLevel) {
        this.requestOrigin = requestOrigin;
        this.direction = direction;
        this.targetLevel = targetLevel;
        this.requestCreationTime = new Timestamp();
    }

    /**
     * Constructor for inside the elevator's requests
     *
     * @param targetLevel wanted level
     */
    public Request(int targetLevel) {
        this(RequestOrigin.INSIDE, null, targetLevel);
    }

    /**
     * Constructor for outside the elevator's requests
     *
     * @param direction wanted direction
     * @param targetLevel wanted level
     */
    public Request(Direction direction, int targetLevel) {
        this(RequestOrigin.OUTSIDE, direction, targetLevel);
    }

    /**
     * Origin of the request (if is inside or outside the elevator)
     *
     * @return Origin of the request
     * @see RequestOrigin to see all returned values
     */
    public RequestOrigin getRequestOrigin() {
        return requestOrigin;
    }

    /**
     * Desired direction of the request (UP or DOWN), only for outside requests
     *
     * @return Direction of the request
     * @see Direction for all allowed directions
     */
    public Direction getDirection() {
        if (direction == null)
            throw new IllegalStateException("Direction available for OUTSIDE requests only. Current: " + getRequestOrigin());

        return direction;
    }

    /**
     * Target level or request
     *
     * @return integer of the destination to reach
     */
    public int getTargetLevel() {
        return targetLevel;
    }

    /**
     * Gives timestamp when request was created
     *
     * @return Timestamp when created
     */
    public Timestamp getCreationTime() {
        return requestCreationTime;
    }


    @Override
    public String toString() {
        return "Request {" +
                "Origin: " + requestOrigin + ", " +
                "Direction: " + direction + ", " +
                "Target level: " + targetLevel + ", " +
                "Creation time: " + requestCreationTime.getMilliseconds() +
                "}";
    }

    @Override
    public int compareTo(Request r) {
        return r.getTargetLevel() - this.getTargetLevel();
    }
}
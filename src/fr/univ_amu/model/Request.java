package fr.univ_amu.model;

/**
 * User request representation, created when user calls elevator or order to go to another level. Works like a Promise
 *
 * @author VIZCAINO Yohan
 */
public class Request {

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
     * Default constructor
     */
    private Request(RequestOrigin requestOrigin, Direction direction, int sourceLevel, int targetLevel) {
        this.requestOrigin = requestOrigin;
        this.direction = direction;
        this.sourceLevel = sourceLevel;
        this.targetLevel = targetLevel;
        this.requestCreationTime = new Timestamp();
    }

    /**
     * Constructor for inside the elevator's requests
     */
    public Request(int sourceLevel, int targetLevel) {
        this(RequestOrigin.INSIDE, null, sourceLevel, targetLevel);
    }

    /**
     * Constructor for outside the elevator's requests
     */
    public Request(Direction direction, int sourceLevel) {
        this(RequestOrigin.OUTSIDE, direction, sourceLevel, -1);
    }

    /**
     * Origin of the request (if is inside or outside of the elevator)
     *
     * @return
     */
    public RequestOrigin getRequestOrigin() {
        return requestOrigin;
    }

    /**
     * Desired direction of the request (UP or DOWN), only for outside requests
     *
     * @return
     */
    public Direction getDirection() {
        if (sourceLevel == -1)
            throw new IllegalStateException("Target level available for OUTSIDE requests only. Current: INSIDE");

        return direction;
    }

    /**
     * Source level of request
     *
     * @return
     */
    public int getSourceLevel() {
        return sourceLevel;
    }

    /**
     * Target level or request
     *
     * @return
     */
    public int getTargetLevel() {
        if (sourceLevel == -1)
            throw new IllegalStateException("Target level available for INSIDE requests only. Current: OUTSIDE");

        return targetLevel;
    }

    /**
     * Gives timestamp when request was created
     *
     * @return
     */
    public Timestamp getCreationTime() {
        return requestCreationTime;
    }


    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("Request {").append("")
                .append("Origin: ").append(requestOrigin).append(", ")
                .append("Direction: ").append(direction).append(", ")
                .append("Source level: ").append(sourceLevel).append(", ")
                .append("Target level: ").append(targetLevel).append(", ")
                .append("Creation time: ").append(requestCreationTime.getMilliseconds())
                .append("}");

        return strBuilder.toString();
    }
}
package fr.univ_amu.model;

/**
 * Requested direction by user
 */
public enum Direction {
    UP,
    DOWN;

    /**
     * Transforms a direction to associated movement
     *
     * @param direction to convert
     * @return Movement object equivalent to given direction
     */
    public static Movement toMovement(Direction direction) {
        return switch (direction) {
            case UP -> Movement.UP;
            case DOWN -> Movement.DOWN;
        };
    }
}

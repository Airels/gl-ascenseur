package fr.univ_amu.model;

/**
 * Requested direction by user
 */
public enum Direction {
    UP,
    DOWN;

    public static Movement toMovement(Direction direction) {
        return switch (direction) {
            case UP -> Movement.UP;
            case DOWN -> Movement.DOWN;
        };
    }
}

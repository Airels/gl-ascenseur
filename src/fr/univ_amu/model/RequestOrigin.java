package fr.univ_amu.model;

/**
 * Origin representation of an user request (inside or outside elevator).
 * A SYSTEM request will always be executed in first
 */
public enum RequestOrigin {
    INSIDE,
    OUTSIDE,
    SYSTEM
}
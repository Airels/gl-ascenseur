package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.utils.SatisfactionStrategy;

/**
 * Representation of the scheduler, or the "thinker" of the control command duo
 */
public interface Scheduler {


    /**
     * Request to add to the scheduler, for future treatment
     *
     * @param request request to add
     */
    void addRequest(Request request);

    /**
     * Sort pending requests, conditions are set up by chosen satisfaction strategy
     *
     * @param currentLevel current level of the elevator
     * @param movement current movement of the elevator
     * @see SatisfactionStrategy
     */
    void sortRequests(int currentLevel, Movement movement);

    /**
     * Getter to retrieve current request, and reset boolean if current request changed to false, because current request has been picked up
     *
     * @return current request to execute, null if none
     */
    Request getCurrentRequest();

    /**
     * Tag current request as satisfied and remove all requests with same level
     */
    void requestSatisfied();

    /**
     * Clear all pending requests
     */
    void clearRequests();

}
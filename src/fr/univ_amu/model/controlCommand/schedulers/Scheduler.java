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
     * @param movement current movement of the elevator
     * @return true if current request has changed, false otherwise
     * @see SatisfactionStrategy
     */
    boolean sortRequests(Movement movement);

    /**
     * Getter to retrieve curernt request, and reset boolean if current request changed to false, because current request has been picked up
     *
     * @return
     */
    public Request getAndResetCurrentRequest();

    /**
     * @param request
     */
    public void requestSatisfied(Request request);

}
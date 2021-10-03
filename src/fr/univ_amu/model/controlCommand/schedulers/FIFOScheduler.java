package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;

import java.util.Queue;

/**
 * FIFO scheduler as described by default satisfaction strategy
 * #see SatisfactionStrategy
 *
 * @author VIZCAINO Yohan
 */
public class FIFOScheduler implements Scheduler {

    private Queue<Request> pendingRequests;

    @Override
    public void addRequest(Request request) {

    }

    @Override
    public boolean sortRequests(Movement movement) {
        return false;
    }

    @Override
    public Request getAndResetCurrentRequest() {
        return null;
    }

    @Override
    public void requestSatisfied(Request request) {

    }

    @Override
    public void clearRequests() {

    }
}
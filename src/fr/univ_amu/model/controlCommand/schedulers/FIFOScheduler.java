package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * FIFO scheduler as described by default satisfaction strategy
 * #see SatisfactionStrategy
 *
 * @author VIZCAINO Yohan
 */
public class FIFOScheduler implements Scheduler {

    private ArrayDeque<Request> pendingRequests;
    private Request currentRequest;
    private boolean requestUpdated;

    public FIFOScheduler() {
        pendingRequests = new ArrayDeque<>();
        requestUpdated = false;
    }

    @Override
    public void addRequest(Request request) {
        pendingRequests.add(request);
    }

    @Override
    public boolean sortRequests(Movement movement) {
        if (pendingRequests.isEmpty()) {
            currentRequest = null;
            return false;
        }

        if (currentRequest != pendingRequests.getFirst()) {
            currentRequest = pendingRequests.getFirst();
            requestUpdated = true;
            return true;
        }

        return false;
    }

    @Override
    public Request getAndResetCurrentRequest() {
        requestUpdated = false;
        return currentRequest;
    }

    @Override
    public void requestSatisfied(Request request) {
        pendingRequests.remove(request);
    }

    @Override
    public void clearRequests() {
        pendingRequests.clear();
    }
}
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

    public FIFOScheduler() {
        pendingRequests = new ArrayDeque<>();
    }

    @Override
    public void addRequest(Request request) {
        pendingRequests.add(request);
    }

    @Override
    public void sortRequests(int currentLevel, Movement movement) {
        if (pendingRequests.isEmpty()) {
            currentRequest = null;
        }

        if (currentRequest != pendingRequests.getFirst()) {
            currentRequest = pendingRequests.getFirst();
        }
    }

    @Override
    public Request getCurrentRequest() {
        return currentRequest;
    }

    @Override
    public void requestSatisfied() {
        pendingRequests.remove();
    }

    @Override
    public void clearRequests() {
        pendingRequests.clear();
    }
}
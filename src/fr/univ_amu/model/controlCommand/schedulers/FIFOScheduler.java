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

    public FIFOScheduler() {
        pendingRequests = new ArrayDeque<>();
    }

    @Override
    public void addRequest(Request request) {
        pendingRequests.add(request);
    }

    @Override
    public void sortRequests(int currentLevel, Movement movement) {

    }

    @Override
    public Request getCurrentRequest() {
        return pendingRequests.peek();
    }

    @Override
    public void requestSatisfied() {
        pendingRequests.poll();
    }

    @Override
    public void clearRequests() {
        pendingRequests.clear();
    }
}
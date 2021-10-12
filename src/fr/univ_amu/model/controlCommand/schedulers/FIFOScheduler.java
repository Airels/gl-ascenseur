package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;

import java.util.ArrayDeque;

/**
 * FIFO scheduler as described by default satisfaction strategy
 *
 * @author VIZCAINO Yohan
 * @see fr.univ_amu.utils.SatisfactionStrategy to see all satisfation strategy supported
 */
public class FIFOScheduler implements Scheduler {

    private ArrayDeque<Request> pendingRequests;
    private Request currentRequest;

    /**
     * Default construtor of FIFO scheduler
     */
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
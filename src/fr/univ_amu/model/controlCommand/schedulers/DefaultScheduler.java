package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.model.RequestOrigin;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default scheduler as described by default satisfaction strategy
 * #see SatisfactionStrategy
 *
 * @author VIZCAINO Yohan
 */
public class DefaultScheduler implements Scheduler {

    private ArrayDeque<Request> pendingRequests;

    /**
     * Default constructor
     */
    public DefaultScheduler() {
        pendingRequests = new ArrayDeque<>();
    }

    @Override
    public void addRequest(Request request) {
        pendingRequests.add(request);
    }

    @Override
    public void sortRequests(int currentLevel, Movement currentMovement) {
        if (pendingRequests.size() == 1)
            return;

        List<Request> firstRequests = new ArrayList<>();
        List<Request> oppositeRequests = new ArrayList<>();
        List<Request> lastRequests = new ArrayList<>();

        for (Request request : pendingRequests) {
            if (currentMovement == Movement.DOWN) {
                if (request.getRequestOrigin() == RequestOrigin.OUTSIDE && Direction.toMovement(request.getDirection()) != currentMovement)
                    oppositeRequests.add(request);
                else if (request.getTargetLevel() < currentLevel)
                    firstRequests.add(request);
                else if (request.getRequestOrigin() == RequestOrigin.INSIDE)
                    oppositeRequests.add(request);
                else
                    lastRequests.add(request);
            } else {
                if (request.getRequestOrigin() == RequestOrigin.OUTSIDE && Direction.toMovement(request.getDirection()) != currentMovement)
                    oppositeRequests.add(request);
                else if (request.getTargetLevel() > currentLevel)
                    firstRequests.add(request);
                else if (request.getRequestOrigin() == RequestOrigin.INSIDE)
                    oppositeRequests.add(request);
                else
                    lastRequests.add(request);
            }
        }

        Collections.sort(firstRequests);
        if (currentMovement == Movement.UP)
            Collections.reverse(firstRequests);

        Collections.sort(oppositeRequests);
        if (currentMovement == Movement.DOWN)
            Collections.reverse(oppositeRequests);

        Collections.sort(lastRequests);
        if (currentMovement == Movement.UP)
            Collections.reverse(lastRequests);

        pendingRequests.clear();
        pendingRequests.addAll(firstRequests);
        pendingRequests.addAll(oppositeRequests);
        pendingRequests.addAll(lastRequests);
    }

    @Override
    public Request getCurrentRequest() {
        return pendingRequests.peek();
    }

    @Override
    public void requestSatisfied() {
        if (pendingRequests.isEmpty()) return;

        Request removedRequest = pendingRequests.pollFirst();
        List<Request> requestsToRemove = new ArrayList<>();

        for (Request request : pendingRequests) {
            if (request.getTargetLevel() == removedRequest.getTargetLevel())
                requestsToRemove.add(request);
        }

        for (Request request : requestsToRemove)
            pendingRequests.remove(request);
    }

    @Override
    public void clearRequests() {
        pendingRequests.clear();
    }
}
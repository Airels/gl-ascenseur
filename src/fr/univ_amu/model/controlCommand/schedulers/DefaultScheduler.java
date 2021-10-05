package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
import fr.univ_amu.model.RequestOrigin;
import fr.univ_amu.utils.Distance;

import java.util.*;

/**
 * Default scheduler as described by default satisfaction strategy
 * #see SatisfactionStrategy
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
        if (request.getRequestOrigin() == RequestOrigin.OUTSIDE)
            pendingRequests.offer(request);
        else {
            Request current = pendingRequests.pollLast();
            pendingRequests.add(request);
            if (current != null)
                pendingRequests.add(current);
        }
    }

    @Override
    public void sortRequests(int currentLevel, Movement movement) {
        ArrayDeque<Request> orderedRequests = new ArrayDeque<>();

        while (!pendingRequests.isEmpty()) {
            Request bestRequest = null;

            for (Request request : pendingRequests) {
                if (request.getRequestOrigin() == RequestOrigin.OUTSIDE && Direction.toMovement(request.getDirection()) == movement) {
                    if (bestRequest == null) {
                        bestRequest = request;
                    } else if (Distance.between(currentLevel, targetLevel(bestRequest)) > Distance.between(currentLevel, targetLevel(request))) {
                        bestRequest = request;
                    }
                }
            }

            for (Request request : pendingRequests) {
                if (request.getRequestOrigin() == RequestOrigin.INSIDE) {
                    if (movement == Movement.UP) {
                        if (currentLevel < targetLevel(request)) {
                            if (bestRequest == null || targetLevel(request) < targetLevel(bestRequest))
                                bestRequest = request;
                        }
                    } else {
                        if (currentLevel > targetLevel(request)) {
                            if (bestRequest == null || targetLevel(request) > targetLevel(bestRequest))
                                bestRequest = request;
                        }
                    }
                }
            }

            if (bestRequest == null) {
                for (Request request : pendingRequests) {
                    if (request.getRequestOrigin() == RequestOrigin.OUTSIDE && Direction.toMovement(request.getDirection()) != movement) {
                        if (bestRequest == null) {
                            bestRequest = request;
                        } else {
                            if (movement == Movement.UP) {
                                if (targetLevel(bestRequest) < targetLevel(request)) {
                                    bestRequest = request;
                                }
                            } else if (movement == Movement.DOWN) {
                                if (targetLevel(bestRequest) > targetLevel(request)) {
                                    bestRequest = request;
                                }
                            }
                        }
                    }
                }
            }

            // ---

            // TODO null = requete interne ?
            orderedRequests.addLast(bestRequest);
            pendingRequests.remove(bestRequest);
        }

        pendingRequests = orderedRequests;
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

    private int targetLevel(Request request) {
        return switch (request.getRequestOrigin()) {
            case INSIDE -> request.getTargetLevel();
            case OUTSIDE -> request.getSourceLevel();
        };
    }
}
package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.Request;
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
        pendingRequests.add(request);
    }

    @Override
    public boolean sortRequests(int currentLevel, Movement movement) {
        Request bestRequest = null;

        for (Request request : pendingRequests) {
            if (bestRequest == null) {
                bestRequest = request;
            } else if (movement == Movement.IDLE || movement == Direction.toMovement(request.getDirection())) {
                if (Distance.between(currentLevel, targetLevel(bestRequest)) > Distance.between(currentLevel, targetLevel(request))) {
                    bestRequest = request;
                }
            }
        }

        return false;
    }

    @Override
    public Request getCurrentRequest() {
        return pendingRequests.getFirst();
    }

    @Override
    public void requestSatisfied(Request request) {
        pendingRequests.remove(request);
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
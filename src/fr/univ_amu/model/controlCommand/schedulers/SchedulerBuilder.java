package fr.univ_amu.model.controlCommand.schedulers;

import fr.univ_amu.model.exceptions.UnhandledStrategyException;
import fr.univ_amu.utils.ExceptionHandler;
import fr.univ_amu.utils.SatisfactionStrategy;

/**
 * Scheduler builder depending on configured satisfaction strategy
 *
 * @author VIZCAINO Yohan
 */
public final class SchedulerBuilder {

    /**
     * Default constructor
     */
    private SchedulerBuilder() {
        ExceptionHandler.showAndExit(new IllegalStateException("SchedulerBuilder cannot be instantiated"));
    }

    /**
     * Build new scheduler depending on requested satisfaction strategy
     *
     * @param strategy strategy to build
     * @return
     */
    public static Scheduler build(SatisfactionStrategy strategy) throws UnhandledStrategyException {
        System.out.println("Selected strategy: " + strategy.name());

        return switch (strategy) {
            case DEFAULT -> new DefaultScheduler();
            case FIFO -> new FIFOScheduler();
            default -> throw new UnhandledStrategyException(strategy);
        };
    }

}
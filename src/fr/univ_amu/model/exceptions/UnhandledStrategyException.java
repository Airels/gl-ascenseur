package fr.univ_amu.model.exceptions;

import fr.univ_amu.utils.SatisfactionStrategy;

/**
 * Exception used when a strategy isn't handled by a method
 *
 * @author VIZCAINO Yohan
 */
public class UnhandledStrategyException extends Exception {

    /**
     * Default constructor
     *
     * @param strategy strategy not handled
     */
    public UnhandledStrategyException(SatisfactionStrategy strategy) {
        super(strategy.name());
    }

    @Override
    public String getMessage() {
        return "Unsupported strategy " + super.getMessage();
    }
}

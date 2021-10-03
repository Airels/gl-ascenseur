package fr.univ_amu.model.exceptions;

import fr.univ_amu.utils.SatisfactionStrategy;

public class UnhandledStrategyException extends Exception {

    public UnhandledStrategyException(SatisfactionStrategy strategy) {
        super(strategy.name());
    }

    @Override
    public String getMessage() {
        return "Unsupported strategy " + super.getMessage();
    }
}

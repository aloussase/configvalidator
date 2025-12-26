package io.github.aloussase.configvalidator.rules;

import io.github.aloussase.configvalidator.ConfigurationVariable;
import io.github.aloussase.configvalidator.Validation;

import java.util.function.Function;

public abstract class Rule {

    protected String selector;

    public Rule(String selector) {
        this.selector = selector;
    }

    public static Rule mk(String selector, Function<ConfigurationVariable, Validation> f) {
        return new Rule(selector) {
            @Override
            protected Validation doApply(ConfigurationVariable subject) {
                return f.apply(subject);
            }
        };
    }

    /**
     * Apply this rule to a given ConfigurationVariable to get the resulting validation.
     *
     * @param subject The configuration variable to apply this Rule to
     * @return The result of the validation
     */
    public Validation applyTo(ConfigurationVariable subject) {
        if ("default".equals(selector) || subject.name().equals(selector)) {
            return doApply(subject);
        }
        // If the Rule does not apply, then there's nothing to validate.
        return new Validation.Success();
    }

    protected abstract Validation doApply(ConfigurationVariable subject);
}

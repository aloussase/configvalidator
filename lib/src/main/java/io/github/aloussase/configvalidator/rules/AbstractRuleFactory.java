package io.github.aloussase.configvalidator.rules;

import io.github.aloussase.configvalidator.Validation;

public abstract class AbstractRuleFactory {

    /**
     * Create a new Rule from the given selector and rule name.
     *
     * @param selector The selector for which the rule applies.
     * @param name     The name of the rule.
     * @return A Rule.
     */
    public static Rule create(String selector, String name) {
        return switch (name) {
            case "no-trailing-whitespace" -> Rule.mk(selector, Validation.noTrailingWhitespace());
            case "no-leading-whitespace" -> Rule.mk(selector, Validation.noLeadingWhitespace());
            case "valid-url" -> Rule.mk(selector, Validation.validUrl());
            default -> throw new IllegalArgumentException("Unknown rule name: " + name);
        };
    }

}

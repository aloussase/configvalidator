package io.github.aloussase.configvalidator.rules.parser;

import io.github.aloussase.configvalidator.rules.Rule;

import java.util.List;

public interface RuleParser {
    /**
     * Parse a list of rules from a given string source.
     *
     * @param source The string from which to parse the rules.
     * @return A list of Rules.
     */
    List<Rule> parse(String source);
}

package io.github.aloussase.configvalidator.rules.parser;

import io.github.aloussase.configvalidator.rules.Rule;

import java.util.List;

/**
 * Implementors of this interface should be able to take a string representing
 * validation rules in a given format (e.g. JSON, YAML or a custom format), parse it
 * and generate a list of {@link  Rule}.
 */
public interface RuleParser {
    /**
     * Parse a list of rules from a given string source.
     * <p>
     * Any parsing errors should be communicated to the end user in the form
     * of exceptions. Better error handling and reporting is planned for future
     * versions.
     *
     * @param source The string from which to parse the rules.
     * @return A list of Rules.
     */
    List<Rule> parse(String source);
}

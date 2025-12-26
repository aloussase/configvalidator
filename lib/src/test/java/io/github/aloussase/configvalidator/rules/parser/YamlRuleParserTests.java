package io.github.aloussase.configvalidator.rules.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class YamlRuleParserTests {

    @Test
    public void givenValidRulesSpecificationContainingOneRuleWhenParsingRulesThenResultHasThatOneRule() {
        final var doc = "rules:\n  default:\n  - name: \"no-trailing-whitespace\"";
        final var parser = new YamlRuleParser();

        final var rules = parser.parse(doc);

        assertEquals(1, rules.size());
        assertNotNull(rules.getFirst());
    }

    @Test
    public void givenValidRulesSpecificationWithTwoRulesWhenParsingRulesThenResultHasTwoRules() {
        final var doc = "rules:\n  default:\n    - name: \"no-trailing-whitespace\"\n    - name: \"no-leading-whitespace\"";
        final var parser = new YamlRuleParser();

        final var rules = parser.parse(doc);

        assertEquals(2, rules.size());
        assertNotNull(rules.getFirst());
    }

    @Test
    public void givenValidRulesSpecificationWithTwoSelectorWithOneRuleEachWhenParsingRulesThenResultHasTwoRules() {
        final var doc = "rules:\n  default:\n    - name: \"no-trailing-whitespace\"\n  go_points_checkout_url:\n    - name: \"valid-url\"";
        final var parser = new YamlRuleParser();

        final var rules = parser.parse(doc);

        assertEquals(2, rules.size());
        assertNotNull(rules.getFirst());
    }

}

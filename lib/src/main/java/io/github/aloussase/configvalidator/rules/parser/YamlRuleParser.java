package io.github.aloussase.configvalidator.rules.parser;

import io.github.aloussase.configvalidator.rules.AbstractRuleFactory;
import io.github.aloussase.configvalidator.rules.Rule;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlRuleParser implements RuleParser {
    private final Yaml yaml;

    public YamlRuleParser() {
        this.yaml = new Yaml();
    }

    @Override
    public List<Rule> parse(String source) {
        final Map<String, Object> map = yaml.load(source);
        final Map<String, Object> configuredRules = (Map<String, Object>) map.get("rules");
        if (configuredRules == null) return new ArrayList<>();
        final List<Rule> rules = new ArrayList<>();
        for (final var entry : configuredRules.entrySet()) {
            final var selector = entry.getKey();
            final var entryRules = (List<Map<String, String>>) entry.getValue();
            for (final var entryRule : entryRules) {
                final var ruleName = (String) entryRule.get("name");
                rules.add(AbstractRuleFactory.create(selector, ruleName));
            }
        }
        return rules;
    }
}

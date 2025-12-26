package io.github.aloussase.configvalidator;

import io.github.aloussase.configvalidator.reporting.ResultsReporter;
import io.github.aloussase.configvalidator.rules.parser.RuleParser;

public class RuleEngine {

    private final ResultsReporter resultsReporter;
    private final RuleParser ruleParser;
    private final ConfigurationVariableSource configurationVariableSource;

    public RuleEngine(ResultsReporter resultsReporter, RuleParser ruleParser, ConfigurationVariableSource configurationVariableSource) {
        this.resultsReporter = resultsReporter;
        this.ruleParser = ruleParser;
        this.configurationVariableSource = configurationVariableSource;
    }

    public void run(String rulesSource, String env) {
        final var rules = ruleParser.parse(rulesSource);
        final var configurationVariables = configurationVariableSource.readConfigurationVariables(env);
        final var validationResults = configurationVariables
                .stream()
                .flatMap(cv -> rules.stream().map(r -> r.applyTo(cv)))
                .toList();
        resultsReporter.report(validationResults);
    }

}

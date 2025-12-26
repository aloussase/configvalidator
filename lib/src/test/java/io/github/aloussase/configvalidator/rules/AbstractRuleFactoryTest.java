package io.github.aloussase.configvalidator.rules;

import io.github.aloussase.configvalidator.ConfigurationVariable;
import io.github.aloussase.configvalidator.Validation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AbstractRuleFactoryTest {

    static record TestCase(
            String selector,
            String ruleName,
            ConfigurationVariable errorCase,
            ConfigurationVariable successCase
    ) {
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(
                        new TestCase(
                                "default",
                                "no-leading-whitespace",
                                new ConfigurationVariable("test", " yo"),
                                new ConfigurationVariable("test", "yo")
                        )
                ),
                Arguments.of(
                        new TestCase(
                                "default",
                                "no-trailing-whitespace",
                                new ConfigurationVariable("test", "yo "),
                                new ConfigurationVariable("test", "yo")
                        )
                ),
                Arguments.of(
                        new TestCase(
                                "default",
                                "valid-url",
                                new ConfigurationVariable("test", "asfds fs"),
                                new ConfigurationVariable("test", "https://example.com")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void givenNoLeadingWhitespaceRuleNameWhenCreatingRuleThenResultingRuleWorksCorrectly(TestCase testCase) {
        final var selector = testCase.selector();
        final var name = testCase.ruleName();

        final var rule = AbstractRuleFactory.create(selector, name);

        assertNotNull(rule);
        assertInstanceOf(Validation.Error.class, rule.applyTo(testCase.errorCase()));
        assertInstanceOf(Validation.Success.class, rule.applyTo(testCase.successCase()));
    }

}
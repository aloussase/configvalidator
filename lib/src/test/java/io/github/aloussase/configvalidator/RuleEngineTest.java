package io.github.aloussase.configvalidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aloussase.configvalidator.reporting.ConsoleReporter;
import io.github.aloussase.configvalidator.rules.parser.YamlRuleParser;
import io.github.aloussase.configvalidator.sources.AndesConfigurationVariableSource;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

class RuleEngineTest {

    ///  NOTE: This test is not to be run in CI.
    @Test
    public void test() {
        final var apiUrl = System.getenv("ANDES_API_URL");
        final var bluePrint = System.getenv("ANDES_BLUEPRINT");
        final var authToken = System.getenv("ANDES_AUTH_TOKEN");
        final var deploymentId = System.getenv("ANDES_DEPLOYMENT_ID");

        if (apiUrl == null || authToken == null || deploymentId == null || bluePrint == null) {
            return;
        }

        final var reporter = new ConsoleReporter();
        final var parser = new YamlRuleParser();

        final var httpClient = new OkHttpClient.Builder().build();
        final var objectMapper = new ObjectMapper();
        final var dataSource = new AndesConfigurationVariableSource(httpClient, apiUrl, bluePrint, authToken, deploymentId, objectMapper);

        final var engine = new RuleEngine(reporter, parser, dataSource);

        try (
                final var is = new InputStreamReader(getClass().getResourceAsStream("/sample-rules.yaml"));
                final var br = new BufferedReader(is)
        ) {
            engine.run(
                    br.lines().collect(Collectors.joining("\n")),
                    "DEV"
            );
        } catch (IOException ignored) {
        }
    }

}
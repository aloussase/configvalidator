package io.github.aloussase.configvalidator.sources;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AndesConfigurationVariableSourceTest {

    @Test
    public void givenRealDataSourceParametersWhenReadingConfigurationVariablesCallReturnsWithSuccess() {
        final var apiUrl = System.getenv("ANDES_API_URL");
        final var bluePrint = System.getenv("ANDES_BLUEPRINT");
        final var authToken = System.getenv("ANDES_AUTH_TOKEN");
        final var deploymentId = System.getenv("ANDES_DEPLOYMENT_ID");

        if (apiUrl == null || authToken == null || deploymentId == null || bluePrint == null) {
            return;
        }

        final var httpClient = new OkHttpClient.Builder().build();
        final var objectMapper = new ObjectMapper();

        final var dataSource = new AndesConfigurationVariableSource(httpClient, apiUrl, bluePrint, authToken, deploymentId, objectMapper);

        final var variables = dataSource.readConfigurationVariables("DEV");

        assertNotNull(variables);
        assertFalse(variables.isEmpty());
    }

}
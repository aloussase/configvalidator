package io.github.aloussase.configvalidator.sources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aloussase.configvalidator.ConfigurationVariable;
import io.github.aloussase.configvalidator.ConfigurationVariableSource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AndesConfigurationVariableSource implements ConfigurationVariableSource {

    private final OkHttpClient httpClient;

    private final String apiUrl;

    private final String bluePrint;

    private final String authToken;

    private final String deploymentId;

    private final ObjectMapper objectMapper;

    public AndesConfigurationVariableSource(OkHttpClient httpClient, String apiUrl, String bluePrint, String authToken, String deploymentId, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.apiUrl = apiUrl;
        this.bluePrint = bluePrint;
        this.authToken = authToken;
        this.deploymentId = deploymentId;
        this.objectMapper = objectMapper;
    }

    // TODO: Better error handling to let the user know what went wrong.

    @Override
    public List<ConfigurationVariable> readConfigurationVariables(String env) {
        final var requestBody = """
                    {
                        "query": {
                            "combinator": "and",
                            "rules": [
                                {
                                    "combinator": "and",
                                     "rules": [
                                        {
                                            "property": "deployment_id",
                                             "operator": "=",
                                             "value": "%s"
                                        },
                                        {
                                             "property": "entorno_name",
                                             "operator": "=",
                                             "value": "%s"
                                        }
                                     ]
                                }
                            ]
                        },
                        "include": ["$title", "value"]
                    }
                """
                .formatted(deploymentId, env)
                .trim();

        final var request = new Request.Builder()
                .post(RequestBody.create(requestBody.getBytes()))
                .url("%s/v1/blueprints/%s/entities/search".formatted(apiUrl, bluePrint))
                .header("Authorization", "Bearer " + authToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        Response response;

        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException ignored) {
            return new ArrayList<>();
        }

        if (!response.isSuccessful()) {
            return new ArrayList<>();
        }

        try (final var inputStream = response.body().byteStream()) {
            final var rootNode = objectMapper.readTree(inputStream);
            return rootNode
                    .get("entities")
                    .valueStream()
                    .map(node -> new ConfigurationVariable(
                            node.get("title").asText(),
                            node.get("properties").get("value").asText()
                    ))
                    .toList();
        } catch (IOException ignored) {
            return new ArrayList<>();
        }
    }
}

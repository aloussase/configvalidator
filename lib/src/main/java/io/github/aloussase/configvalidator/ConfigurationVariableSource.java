package io.github.aloussase.configvalidator;

import java.util.List;

public interface ConfigurationVariableSource {
    /**
     * Read a list of configuration variables defined for the specified environment.
     *
     * @param env The environment for which to fetch configuration variables.
     * @return A list of the configuration variables defined in the given environment.
     */
    List<ConfigurationVariable> readConfigurationVariables(String env);
}

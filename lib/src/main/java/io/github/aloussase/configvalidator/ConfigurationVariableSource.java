package io.github.aloussase.configvalidator;

import java.util.List;

/**
 * Implementors of this interface should be able to fetch configuration variables
 * from a given source (HTTP API, file; etc.) corresponding to the provided environment.
 * <p>
 * Implementors are free to ignore the {@code env} argument as well if not needed.
 */
public interface ConfigurationVariableSource {
    /**
     * Read a list of configuration variables defined for the specified environment.
     * <p>
     * This method should fail silently and return an empty list in the case of error.
     * You may want to log errors to a file for bookkeeping. You might also want to throw
     * and exception, in which case it will be visible to the end user.
     * <p>
     * Better error reporting is planned for future versions.
     *
     * @param env The environment for which to fetch configuration variables.
     * @return A list of the configuration variables defined in the given environment.
     */
    List<ConfigurationVariable> readConfigurationVariables(String env);
}

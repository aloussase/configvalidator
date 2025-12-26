package io.github.aloussase.configvalidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

public sealed interface Validation permits Validation.Success, Validation.Error {

    static Function<ConfigurationVariable, Validation> noLeadingWhitespace() {
        return (ConfigurationVariable cv) -> {
            if (cv.value().startsWith(" ")) {
                return new Error(cv, "Unexpected whitespace at the start of " + cv.name());
            } else {
                return new Success();
            }
        };
    }

    static Function<ConfigurationVariable, Validation> noTrailingWhitespace() {
        return (ConfigurationVariable cv) -> {
            if (cv.value().endsWith(" ")) {
                return new Error(cv, "Unexpected whitespace at the end of " + cv.name());
            } else {
                return new Success();
            }
        };
    }

    static Function<ConfigurationVariable, Validation> validUrl() {
        return (ConfigurationVariable cv) -> {
            try {
                new URI(cv.value());
                return new Success();
            } catch (URISyntaxException | IllegalArgumentException ignored) {
                return new Error(cv, "Invalid URL: " + cv.name());
            }
        };
    }

    record Success() implements Validation {
    }

    record Error(
            ConfigurationVariable subject,
            String message
    ) implements Validation {
    }

}

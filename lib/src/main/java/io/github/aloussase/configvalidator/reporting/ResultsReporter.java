package io.github.aloussase.configvalidator.reporting;

import io.github.aloussase.configvalidator.Validation;

import java.util.List;

/**
 * Implementors of this interface should be able to take a list of {@link Validation}s
 * and report it in some arbitrary way (e.g. through an HTML document, console output; etc.).
 */
public interface ResultsReporter {
    /**
     * Report the results of configuration variable configuration.
     *
     * @param validationResults The validation results to report.
     */
    void report(List<Validation> validationResults);
}

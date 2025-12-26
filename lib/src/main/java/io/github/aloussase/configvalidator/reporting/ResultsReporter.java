package io.github.aloussase.configvalidator.reporting;

import io.github.aloussase.configvalidator.Validation;

import java.util.List;

public interface ResultsReporter {
    /**
     * Report the results of configuration variable configuration.
     *
     * @param validationResults The validation results to report.
     */
    void report(List<Validation> validationResults);
}

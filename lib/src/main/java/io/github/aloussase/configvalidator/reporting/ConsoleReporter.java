package io.github.aloussase.configvalidator.reporting;

import io.github.aloussase.configvalidator.Validation;

import java.util.ArrayList;
import java.util.List;

final public class ConsoleReporter implements ResultsReporter {
    @Override
    public void report(List<Validation> validationResults) {
        final var errors = new ArrayList<Validation.Error>();
        int successCount = 0;

        for (final var result : validationResults) {
            switch (result) {
                case Validation.Error err -> errors.add(err);
                default -> successCount += 1;
            }
        }

        final var sb = new StringBuilder("=== Validation Results ===\n\n");

        sb
                .append("Success: ").append(successCount).append(", ")
                .append("Errors: ").append(errors.size()).append("\n");

        sb.append("Error Report:\n\n");

        for (final var err : errors) {
            sb.append(err.subject().name());
            sb.append(": ");
            sb.append(err.message());
            sb.append("\n");
        }

        System.out.println(sb);
    }
}

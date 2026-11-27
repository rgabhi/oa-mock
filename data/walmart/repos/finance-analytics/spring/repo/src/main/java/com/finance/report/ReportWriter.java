package com.finance.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class ReportWriter {
    /** Where the reports go: output/ in the project folder. */
    public static final Path OUTPUT_DIR = Path.of("output");

    private final ObjectMapper json = new ObjectMapper();

    /** Saves an API response as output/<city>.json (pretty-printed), replacing any earlier file for that city. */
    public void saveReport(String city, Object data) {
        // TODO: write `data` as JSON to output/<city>.json (create the folder if it's missing)
    }
}

package com.finance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.report.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** Shared helpers for the finance API tests. Each test starts with no saved reports. */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class FinanceTestSupport {
    protected static final ObjectMapper JSON = new ObjectMapper();

    @Autowired protected MockMvc mvc;

    @BeforeEach
    @AfterEach
    void clean() throws IOException {
        if (Files.exists(ReportWriter.OUTPUT_DIR)) {
            try (Stream<Path> files = Files.walk(ReportWriter.OUTPUT_DIR)) {
                for (Path p : files.sorted(Comparator.reverseOrder()).toList()) {
                    Files.delete(p);
                }
            }
        }
    }

    protected MockHttpServletResponse cityTotals(String city) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get("/transactions/location/{city}", city)).andReturn().getResponse();
    }

    protected MockHttpServletResponse userTotals(Object id, String city) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get("/transaction/{id}/location/{city}", id, city)).andReturn().getResponse();
    }

    protected MockHttpServletResponse summary(String city) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/transactions/location/{city}", city)).andReturn().getResponse();
    }

    protected static JsonNode json(MockHttpServletResponse response) throws Exception {
        return JSON.readTree(response.getContentAsString());
    }

    protected static JsonNode json(String text) throws Exception {
        return JSON.readTree(text);
    }

    /** The saved report for a city, or null when there is none. */
    protected static JsonNode savedReport(String city) throws Exception {
        Path file = ReportWriter.OUTPUT_DIR.resolve(city + ".json");
        return Files.exists(file) ? JSON.readTree(file.toFile()) : null;
    }
}

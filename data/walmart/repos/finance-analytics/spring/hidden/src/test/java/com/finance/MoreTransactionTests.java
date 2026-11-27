package com.finance;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** More integrated tests (hidden in the test: they run when you submit). */
class MoreTransactionTests extends FinanceTestSupport {
    @Test
    void cityNamesMatchExactly() throws Exception {
        assertEquals(404, cityTotals("ahmedabad").getStatus());
        assertEquals(json("{\"totalDebit\": 1250, \"totalCredit\": 5000}"), json(cityTotals("Bengaluru")));
        assertEquals(json("{\"totalDebit\": 200, \"totalCredit\": 0}"), json(cityTotals("Pune")));
    }

    @Test
    void theUserIdInThePathIsANumber() throws Exception {
        assertEquals(404, userTotals("abc", "Pune").getStatus());
        assertEquals(json("{\"totalDebit\": 200, \"totalCredit\": 0}"), json(userTotals(2, "Pune")));
        assertEquals(json("{\"totalDebit\": 0, \"totalCredit\": 2450}"), json(userTotals(5, "Delhi")));
    }

    @Test
    void theExampleFromTheTaskAhmedabad() throws Exception {
        MockHttpServletResponse res = summary("Ahmedabad");
        assertEquals(json("[{\"userId\": 1, \"totalDebit\": 500, \"totalCredit\": 200},"
                + "{\"userId\": 2, \"totalDebit\": 0, \"totalCredit\": 1000}]"), json(res));
        assertEquals(json(res), savedReport("Ahmedabad"));
    }

    @Test
    void a404SavesNoFileAndACityGetsItsOwnFile() throws Exception {
        summary("Chennai");
        assertNull(savedReport("Chennai"));
        summary("Pune");
        summary("Bengaluru");
        assertEquals(json("[{\"userId\": 2, \"totalDebit\": 200, \"totalCredit\": 0}]"), savedReport("Pune"));
        assertEquals(json("[{\"userId\": 7, \"totalDebit\": 1250, \"totalCredit\": 5000}]"), savedReport("Bengaluru"));
    }
}

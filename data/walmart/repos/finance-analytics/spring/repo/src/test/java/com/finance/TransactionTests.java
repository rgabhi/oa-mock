package com.finance;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The integrated tests for the finance API. Run them with:  mvn test */
class TransactionTests extends FinanceTestSupport {
    private static final String NOT_FOUND = "{\"message\": \"No transactions found\"}";

    @Test
    void returnsTheDebitAndCreditTotalsOfACity() throws Exception {
        MockHttpServletResponse res = cityTotals("Ahmedabad");
        assertEquals(200, res.getStatus(), res.getContentAsString());
        assertEquals(json("{\"totalDebit\": 500, \"totalCredit\": 1200}"), json(res));
        assertEquals(json("{\"totalDebit\": 1250, \"totalCredit\": 1500}"), json(cityTotals("Mumbai")));
    }

    @Test
    void returnsTheTotalsOfOneUserInACity() throws Exception {
        MockHttpServletResponse res = userTotals(1, "Ahmedabad");
        assertEquals(200, res.getStatus(), res.getContentAsString());
        assertEquals(json("{\"totalDebit\": 500, \"totalCredit\": 200}"), json(res));
        assertEquals(json("{\"totalDebit\": 300, \"totalCredit\": 0}"), json(userTotals(1, "Mumbai")));
    }

    @Test
    void summarisesEveryUserInACity() throws Exception {
        MockHttpServletResponse res = summary("Mumbai");
        assertEquals(200, res.getStatus(), res.getContentAsString());
        assertEquals(json("[{\"userId\": 1, \"totalDebit\": 300, \"totalCredit\": 0},"
                + "{\"userId\": 3, \"totalDebit\": 700, \"totalCredit\": 1500},"
                + "{\"userId\": 4, \"totalDebit\": 250, \"totalCredit\": 0}]"), json(res));
    }

    @Test
    void savesTheSummaryToAJSONFile() throws Exception {
        MockHttpServletResponse res = summary("Delhi");
        assertEquals(200, res.getStatus(), res.getContentAsString());
        assertEquals(json("[{\"userId\": 5, \"totalDebit\": 0, \"totalCredit\": 2450},"
                + "{\"userId\": 6, \"totalDebit\": 800, \"totalCredit\": 0}]"), savedReport("Delhi"));
        assertEquals(json(res), savedReport("Delhi"));
    }

    @Test
    void unknownCitiesAndUsersGetA404() throws Exception {
        MockHttpServletResponse city = cityTotals("Chennai");
        assertEquals(404, city.getStatus());
        assertEquals(json(NOT_FOUND), json(city));
        assertEquals(404, userTotals(9, "Ahmedabad").getStatus());
        MockHttpServletResponse elsewhere = userTotals(3, "Ahmedabad");    // user 3 only has transactions in Mumbai
        assertEquals(404, elsewhere.getStatus());
        assertEquals(json(NOT_FOUND), json(elsewhere));
        MockHttpServletResponse post = summary("Chennai");
        assertEquals(404, post.getStatus());
        assertEquals(json(NOT_FOUND), json(post));
    }
}

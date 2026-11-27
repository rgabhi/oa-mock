package com.finance.controller;

import com.finance.data.TransactionData;
import com.finance.model.Transaction;
import com.finance.report.ReportWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private static final Map<String, String> NOT_FOUND = Map.of("message", "No transactions found");

    private final ReportWriter reports;

    public TransactionController(ReportWriter reports) {
        this.reports = reports;
    }

    /** Total DEBIT and total CREDIT amounts of a list of transactions. */
    static Map<String, Long> totals(List<Transaction> list) {
        // TODO: add up the DEBIT amounts and the CREDIT amounts of `list`
        Map<String, Long> out = new LinkedHashMap<>();
        out.put("totalDebit", 0L);
        out.put("totalCredit", 0L);
        return out;
    }

    private static List<Transaction> inCity(String city) {
        return TransactionData.TRANSACTIONS.stream().filter(t -> t.city().equals(city)).toList();
    }

    /** API 1. GET /transactions/location/{city} */
    @GetMapping("/transactions/location/{city}")
    public ResponseEntity<?> getCityTotals(@PathVariable String city) {
        // TODO: 200 with the city's totals, or 404 with NOT_FOUND
        return ResponseEntity.status(501).body(Map.of("message", "Not implemented"));
    }

    /** API 2. GET /transaction/{id}/location/{city} */
    @GetMapping("/transaction/{id}/location/{city}")
    public ResponseEntity<?> getUserCityTotals(@PathVariable String id, @PathVariable String city) {
        // TODO: 200 with the totals of user {id} in {city}, or 404 with NOT_FOUND
        return ResponseEntity.status(501).body(Map.of("message", "Not implemented"));
    }

    /** API 3. POST /transactions/location/{city} */
    @PostMapping("/transactions/location/{city}")
    public ResponseEntity<?> postCityUserSummary(@PathVariable String city) {
        // TODO: one {userId, totalDebit, totalCredit} per user in {city}, sorted by userId;
        //       save it with reports.saveReport and return it, or 404 with NOT_FOUND
        return ResponseEntity.status(501).body(Map.of("message", "Not implemented"));
    }
}

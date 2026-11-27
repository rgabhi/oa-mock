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
        Map<String, Long> out = new LinkedHashMap<>();
        out.put("totalDebit", list.stream().filter(t -> "DEBIT".equals(t.type())).mapToLong(Transaction::amount).sum());
        out.put("totalCredit", list.stream().filter(t -> "CREDIT".equals(t.type())).mapToLong(Transaction::amount).sum());
        return out;
    }

    private static List<Transaction> inCity(String city) {
        return TransactionData.TRANSACTIONS.stream().filter(t -> t.city().equals(city)).toList();
    }

    /** API 1. GET /transactions/location/{city} */
    @GetMapping("/transactions/location/{city}")
    public ResponseEntity<?> getCityTotals(@PathVariable String city) {
        List<Transaction> matching = inCity(city);
        if (matching.isEmpty()) {
            return ResponseEntity.status(404).body(NOT_FOUND);
        }
        return ResponseEntity.ok(totals(matching));
    }

    /** API 2. GET /transaction/{id}/location/{city} */
    @GetMapping("/transaction/{id}/location/{city}")
    public ResponseEntity<?> getUserCityTotals(@PathVariable String id, @PathVariable String city) {
        List<Transaction> matching = id.matches("\\d{1,18}")
                ? inCity(city).stream().filter(t -> t.userId() == Long.parseLong(id)).toList()
                : List.of();
        if (matching.isEmpty()) {
            return ResponseEntity.status(404).body(NOT_FOUND);
        }
        return ResponseEntity.ok(totals(matching));
    }

    /** API 3. POST /transactions/location/{city} */
    @PostMapping("/transactions/location/{city}")
    public ResponseEntity<?> postCityUserSummary(@PathVariable String city) {
        List<Transaction> matching = inCity(city);
        if (matching.isEmpty()) {
            return ResponseEntity.status(404).body(NOT_FOUND);
        }
        List<Map<String, Object>> summary = new TreeSet<>(matching.stream().map(Transaction::userId).toList()).stream()
                .map(userId -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("userId", userId);
                    row.putAll(totals(matching.stream().filter(t -> t.userId() == userId).toList()));
                    return row;
                })
                .toList();
        reports.saveReport(city, summary);
        return ResponseEntity.ok(summary);
    }
}

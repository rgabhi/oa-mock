package com.finance.data;

import com.finance.model.Transaction;
import java.util.List;

/** The dataset, already imported into the application. No database is needed. */
public final class TransactionData {
    public static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(101, 1, "Ahmedabad", "DEBIT", 500),
            new Transaction(102, 2, "Ahmedabad", "CREDIT", 1000),
            new Transaction(103, 1, "Ahmedabad", "CREDIT", 200),
            new Transaction(104, 3, "Mumbai", "DEBIT", 700),
            new Transaction(105, 3, "Mumbai", "CREDIT", 1500),
            new Transaction(106, 4, "Mumbai", "DEBIT", 250),
            new Transaction(107, 1, "Mumbai", "DEBIT", 300),
            new Transaction(108, 5, "Delhi", "CREDIT", 2000),
            new Transaction(109, 5, "Delhi", "CREDIT", 450),
            new Transaction(110, 6, "Delhi", "DEBIT", 800),
            new Transaction(111, 2, "Pune", "DEBIT", 120),
            new Transaction(112, 2, "Pune", "DEBIT", 80),
            new Transaction(113, 7, "Bengaluru", "CREDIT", 5000),
            new Transaction(114, 7, "Bengaluru", "DEBIT", 1250));

    private TransactionData() {}
}

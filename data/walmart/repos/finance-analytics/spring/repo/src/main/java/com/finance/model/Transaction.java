package com.finance.model;

/** One transaction: type is DEBIT or CREDIT. */
public record Transaction(long transactionId, long userId, String city, String type, long amount) {}

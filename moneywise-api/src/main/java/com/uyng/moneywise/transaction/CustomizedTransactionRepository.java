package com.uyng.moneywise.transaction;

public interface CustomizedTransactionRepository {
    Transaction saveWithChildrenTransactions(Transaction transaction);
}

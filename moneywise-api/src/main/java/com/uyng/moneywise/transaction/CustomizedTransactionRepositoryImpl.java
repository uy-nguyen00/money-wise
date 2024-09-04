package com.uyng.moneywise.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomizedTransactionRepositoryImpl implements CustomizedTransactionRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Transaction saveWithChildrenTransactions(Transaction transaction) {
        transaction.addChildrenTransactions(transaction.getChildrenTransactions());
        for (Transaction child : transaction.getChildrenTransactions()) {
            child.setParentTransaction(transaction);
            entityManager.persist(child);
        }
    }
}

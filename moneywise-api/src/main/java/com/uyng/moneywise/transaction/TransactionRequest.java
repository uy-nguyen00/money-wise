package com.uyng.moneywise.transaction;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record TransactionRequest(
        Integer categoryId,
        @NotNull
        Double amount,
        LocalDate date,
        String description,
        Integer parentId,
        Set<Integer> childrenIds
) {}

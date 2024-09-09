package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TransactionRequest(
        Integer categoryId,
        String description,

        @NotNull
        Double amount,

        LocalDate date
) {}

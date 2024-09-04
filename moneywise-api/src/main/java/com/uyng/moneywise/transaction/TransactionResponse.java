package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.CategoryResponse;

import java.time.LocalDate;
import java.util.List;

public record TransactionResponse(
        Integer id,
        CategoryResponse category,
        Double amount,
        LocalDate date,
        String description
) {}

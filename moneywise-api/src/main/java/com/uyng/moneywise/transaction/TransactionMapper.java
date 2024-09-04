package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import com.uyng.moneywise.category.CategoryMapper;
import com.uyng.moneywise.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final CategoryMapper categoryMapper;

    public Transaction toTransaction(
            TransactionRequest request,
            Category category,
            Transaction parentTransaction
    ) {
        return Transaction.builder()
                .amount(request.amount())
                .category(category)
                .date(request.date())
                .description(request.description())
                .parentTransaction(parentTransaction)
                .build();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(transaction.getCategory());

        return new TransactionResponse(
                transaction.getId(),
                categoryResponse,
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription()
        );
    }
}

package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import com.uyng.moneywise.category.CategoryMapper;
import com.uyng.moneywise.category.CategoryRepository;
import com.uyng.moneywise.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public Transaction toTransaction(TransactionRequest request) {
        Category category = categoryRepository.findById(request.categoryId()).orElse(null);

        return Transaction.builder()
               .amount(request.amount())
               .category(category)
               .description(request.description())
               .date(request.date())
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

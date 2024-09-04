package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import com.uyng.moneywise.category.CategoryRepository;
import com.uyng.moneywise.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Category category = categoryRepository.findByIdAndUserEmail(request.categoryId(), user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Category not found."));

        Transaction parentTransaction = transactionRepository.findByIdAndUser(request.parentId(), user)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found."));

        Transaction transaction = transactionMapper.toTransaction(request, category, parentTransaction);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        Set<Transaction> childrenTransactions = request.childrenIds()
                .stream()
                .map(id -> transactionRepository.findByIdAndUser(id, user)
                        .orElseThrow(() -> new EntityNotFoundException("Transaction not found.")))
                .collect(Collectors.toSet());
        Set<Transaction> transactions = savedTransaction.addChildrenTransactions(childrenTransactions);
        transactionRepository.saveAll(transactions);

        return transactionMapper.toTransactionResponse(savedTransaction);
    }

}

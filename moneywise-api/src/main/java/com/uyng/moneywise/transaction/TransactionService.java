package com.uyng.moneywise.transaction;

import com.uyng.moneywise.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(savedTransaction);
    }
}

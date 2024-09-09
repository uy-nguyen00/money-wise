package com.uyng.moneywise.transaction;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody TransactionRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.createTransaction(request, connectedUser));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findAllTransactionsByUser(Authentication connectedUser) {
        return ResponseEntity.ok(service.findAllTransactionsByUser(connectedUser));
    }
}

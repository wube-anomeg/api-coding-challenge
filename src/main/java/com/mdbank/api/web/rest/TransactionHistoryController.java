package com.mdbank.api.web.rest;
import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import com.mdbank.api.dto.TransactionHistoryDTO;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.service.TransactionHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;
    private final AccountService accountService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService, AccountService accountService) {
        this.transactionHistoryService = transactionHistoryService;
        this.accountService = accountService;
    }

    @GetMapping("/history/{accountId}")
    public ResponseEntity<?> getTransactionHistoryForAccount(@PathVariable Long accountId) {
        // Retrieve the account by its ID
        Optional<Account> optionalAccount  = accountService.getAccountById(accountId);

        // Check if the account exists
        if (optionalAccount.isEmpty()) {
            String message = String.format("Account not found with ID: " + accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        // Retrieve the transaction history for the account
        List<TransactionHistory> transactions = transactionHistoryService.getTransactionsForAccount(optionalAccount.get());

        // Map the TransactionHistory objects to TransactionDTO objects
        List<TransactionHistoryDTO> transactionDTOs = transactions.stream()
                .map(this::convertToTransactionDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionDTOs);

    }

    // Helper method to convert DTO
    private TransactionHistoryDTO convertToTransactionDTO(TransactionHistory transaction) {
       return TransactionHistoryDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .sourceAccountNumber(transaction.getSourceAccount().getAccountNumber())
                .targetAccountNumber(transaction.getTargetAccount().getAccountNumber())
                .build();
    }
}

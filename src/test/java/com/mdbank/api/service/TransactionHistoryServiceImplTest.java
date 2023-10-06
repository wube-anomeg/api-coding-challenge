package com.mdbank.api.service;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import com.mdbank.api.repository.TransactionHistoryRepository;

import com.mdbank.api.service.iml.TransactionHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionHistoryServiceImplTest {

    @InjectMocks
    private TransactionHistoryServiceImpl transactionHistoryService;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactionsForAccount() {
        // Arrange
        Account account = new Account();
        account.setId(1L);

        TransactionHistory transaction1 = new TransactionHistory();
        transaction1.setSourceAccount(account);
        transaction1.setAmount(100.0);

        TransactionHistory transaction2 = new TransactionHistory();
        transaction2.setTargetAccount(account);
        transaction2.setAmount(200.0);

        List<TransactionHistory> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionHistoryRepository.findBySourceAccountOrTargetAccount(account, account)).thenReturn(transactions);

        // Act
        List<TransactionHistory> result = transactionHistoryService.getTransactionsForAccount(account);

        // Assert
        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals(200.0, result.get(1).getAmount());

        verify(transactionHistoryRepository, times(1)).findBySourceAccountOrTargetAccount(account, account);
    }

    @Test
    public void testGetTransactionsForAccount_NoTransactions() {
        // Arrange
        Account account = new Account();
        account.setId(1L);

        when(transactionHistoryRepository.findBySourceAccountOrTargetAccount(account, account)).thenReturn(new ArrayList<>());

        // Act
        List<TransactionHistory> result = transactionHistoryService.getTransactionsForAccount(account);

        // Assert
        assertEquals(0, result.size());

        verify(transactionHistoryRepository, times(1)).findBySourceAccountOrTargetAccount(account, account);
    }
}

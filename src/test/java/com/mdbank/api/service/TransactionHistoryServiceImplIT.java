package com.mdbank.api.service;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import com.mdbank.api.repository.TransactionHistoryRepository;

import com.mdbank.api.service.iml.TransactionHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class TransactionHistoryServiceImplIT {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private TransactionHistoryService transactionHistoryService;

    @BeforeEach
    public void setup() {
        transactionHistoryService = new TransactionHistoryServiceImpl(transactionHistoryRepository);
    }

    @Test
    public void testGetTransactionsForAccount() {
        // Create a test account
        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setBalance(1000.0);

        // Save the account using the entity manager
        testEntityManager.persist(account);

        // Create a test transaction history
        TransactionHistory transaction = new TransactionHistory();
        transaction.setSourceAccount(account);
        transaction.setTargetAccount(account);
        transaction.setAmount(500.0);
        transaction.setTimestamp(LocalDateTime.now());

        // Save the transaction using the entity manager
        testEntityManager.persist(transaction);

        // Get the list of transaction history for the account using the service
        Account persistedAccount = testEntityManager.find(Account.class, account.getId());
        assertNotNull(persistedAccount);
        assertNotNull(persistedAccount.getId());

        // Retrieve the transaction history for the account using the service
        List<TransactionHistory> transactions = transactionHistoryService.getTransactionsForAccount(persistedAccount);

        // Assert that the list is not empty and contains the test transaction
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(500.0, transactions.get(0).getAmount());
    }

}

package web.rest;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import com.mdbank.api.dto.TransactionHistoryDTO;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.service.TransactionHistoryService;
import com.mdbank.api.web.rest.TransactionHistoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionHistoryControllerTest {

    @InjectMocks
    private TransactionHistoryController transactionHistoryController;

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactionHistoryForAccount_AccountExists() {
        // Arrange
        Long accountId = 1L;

        Account account = new Account();
        account.setId(accountId);

        TransactionHistory transaction1 = new TransactionHistory();
        transaction1.setId(1L);
        transaction1.setAmount(100.0);
        transaction1.setTimestamp(LocalDateTime.now());
        transaction1.setSourceAccount(account);
        transaction1.setTargetAccount(account);

        TransactionHistory transaction2 = new TransactionHistory();
        transaction2.setId(2L);
        transaction2.setAmount(200.0);
        transaction2.setTimestamp(LocalDateTime.now());
        transaction2.setSourceAccount(account);
        transaction2.setTargetAccount(account);

        List<TransactionHistory> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(account));
        when(transactionHistoryService.getTransactionsForAccount(account)).thenReturn(transactions);

        // Act
        ResponseEntity<?> responseEntity = transactionHistoryController.getTransactionHistoryForAccount(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<TransactionHistoryDTO> result = (List<TransactionHistoryDTO>) responseEntity.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());

        TransactionHistoryDTO firstTransaction = result.get(0);
        assertEquals(transaction1.getId(), firstTransaction.getId());
        assertEquals(transaction1.getAmount(), firstTransaction.getAmount());
        assertEquals(transaction1.getTimestamp(), firstTransaction.getTimestamp());
        assertEquals(transaction1.getSourceAccount().getAccountNumber(), firstTransaction.getSourceAccountNumber());
        assertEquals(transaction1.getTargetAccount().getAccountNumber(), firstTransaction.getTargetAccountNumber());

        TransactionHistoryDTO secondTransaction = result.get(1);
        assertEquals(transaction2.getId(), secondTransaction.getId());
        assertEquals(transaction2.getAmount(), secondTransaction.getAmount());
        assertEquals(transaction2.getTimestamp(), secondTransaction.getTimestamp());
        assertEquals(transaction2.getSourceAccount().getAccountNumber(), secondTransaction.getSourceAccountNumber());
        assertEquals(transaction2.getTargetAccount().getAccountNumber(), secondTransaction.getTargetAccountNumber());
    }

    @Test
    public void testGetTransactionHistoryForAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;

        when(accountService.getAccountById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = transactionHistoryController.getTransactionHistoryForAccount(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

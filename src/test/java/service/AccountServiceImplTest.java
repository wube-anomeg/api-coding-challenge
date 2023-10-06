package service;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.AccountRepository;
import com.mdbank.api.repository.TransactionHistoryRepository;
import com.mdbank.api.service.iml.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        String accountNumber = "123456";
        double initialDeposit = 100.0;

        when(accountRepository.existsByCustomerAndAccountNumber(customer, accountNumber)).thenReturn(false);

        Account createdAccount = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(createdAccount);

        // Act
        Account result = accountService.createAccount(customer, accountNumber, initialDeposit);

        // Assert
        assertEquals(createdAccount, result);
    }
    @Test
    public void testCreateAccount_AccountNumberInUse() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        String accountNumber = "123456";
        double initialDeposit = 100.0;

        when(accountRepository.existsByCustomerAndAccountNumber(customer, accountNumber)).thenReturn(true);

        // Act
        Account result = accountService.createAccount(customer, accountNumber, initialDeposit);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateAccount_DatabaseConstraintViolation() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        String accountNumber = "123456";
        double initialDeposit = 100.0;

        when(accountRepository.existsByCustomerAndAccountNumber(customer, accountNumber)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenThrow(new DataIntegrityViolationException("Database constraint violation"));

        // Act
        Account result = accountService.createAccount(customer, accountNumber, initialDeposit);

        // Assert
        assertNull(result);
    }
    @Test
    public void testTransferAmount_Success() {
        // Arrange
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        double amount = 50.0;

        Account sourceAccount = new Account();
        sourceAccount.setId(sourceAccountId);
        sourceAccount.setBalance(100.0);

        Account targetAccount = new Account();
        targetAccount.setId(targetAccountId);
        targetAccount.setBalance(200.0);

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // Act
        String result = accountService.transferAmount(sourceAccountId, targetAccountId, amount);

        // Assert
        assertEquals("OK", result);
        assertEquals(50.0, sourceAccount.getBalance()); // Check source account balance
        assertEquals(250.0, targetAccount.getBalance()); // Check target account balance
    }

    @Test
    public void testTransferAmount_InsufficientBalance() {
        // Arrange
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        double amount = 150.0;

        Account sourceAccount = new Account();
        sourceAccount.setId(sourceAccountId);
        sourceAccount.setBalance(100.0);

        Account targetAccount = new Account();
        targetAccount.setId(targetAccountId);
        targetAccount.setBalance(200.0);

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // Act
        String result = accountService.transferAmount(sourceAccountId, targetAccountId, amount);

        // Assert
        assertEquals("Insufficient balance in the source account.", result);
        // Check source account balance(should not change)
        assertEquals(100.0, sourceAccount.getBalance());
        // Check target account balance (should not change)
        assertEquals(200.0, targetAccount.getBalance());
    }

    @Test
    public void testTransferAmount_InvalidAccounts() {
        // Arrange
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        double amount = 50.0;

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.empty());
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.empty());

        // Act
        String result = accountService.transferAmount(sourceAccountId, targetAccountId, amount);

        // Assert
        assertEquals("Invalid source or target account.", result);
    }

    @Test
    public void testGetAccountById_AccountFound() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> result = accountService.getAccountById(accountId);

        // Assert
        assertEquals(Optional.of(account), result);
    }

    @Test
    public void testGetAccountById_AccountNotFound() {
        // Arrange
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.getAccountById(accountId);

        // Assert
        assertEquals(Optional.empty(), result);
    }
}

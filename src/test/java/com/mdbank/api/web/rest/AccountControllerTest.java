package com.mdbank.api.web.rest;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.dto.AccountDTO;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.service.CustomerService;
import com.mdbank.api.web.rest.AccountController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBankAccount_Success() {
        // Arrange

        AccountDTO accountDto = AccountDTO.builder()
                .customerId(1L)
                .accountNumber("123456")
                .initialDeposit(100.0)
                .build();

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setAccountNumber("123456");
        createdAccount.setBalance(100.0);

        when(accountService.createAccount(customer, "123456", 100.0)).thenReturn(createdAccount);

        // Act
        ResponseEntity<?> responseEntity = accountController.createBankAccount(accountDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AccountDTO responseAccountDto = (AccountDTO) responseEntity.getBody();
        assertEquals(1L, responseAccountDto.getCustomerId());
        assertEquals("123456", responseAccountDto.getAccountNumber());
        assertEquals(100.0, responseAccountDto.getInitialDeposit());
    }

    @Test
    public void testCreateBankAccount_CustomerNotFound() {
        // Arrange
        AccountDTO accountDto = AccountDTO.builder()
                .customerId(1L)
                .initialDeposit(100.0) // Set a non-null initial deposit value
                .build();

        when(customerService.getCustomerById(1L)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = accountController.createBankAccount(accountDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Customer with id 1 not found", responseEntity.getBody());
    }

    @Test
    public void testCreateBankAccount_NonPositiveInitialDeposit() {
        // Arrange
        AccountDTO accountDto = AccountDTO.builder()
                .customerId(1L)
                .initialDeposit(0.0)
                .build();

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        // Act
        ResponseEntity<?> responseEntity = accountController.createBankAccount(accountDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Initial deposit must be greater than 0", responseEntity.getBody());
    }

    @Test
    public void testGetAccountBalance_Success() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(500.0);

        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(account));

        // Act
        ResponseEntity<?> responseEntity = accountController.getAccountBalance(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(500.0, responseEntity.getBody());
    }

    @Test
    public void testGetAccountBalance_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountService.getAccountById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = accountController.getAccountBalance(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Account with accountId 1 not found", responseEntity.getBody());
    }
}

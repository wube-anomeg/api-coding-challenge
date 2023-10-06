package com.mdbank.api.service;

import com.mdbank.api.IntegrationTest;
import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class AccountServiceImplIT {


    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        // Create a test customer
        Customer customer = new Customer();
        customer.setName("John");
        customerService.createCustomer(customer);
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateAccount_Success() {
        // Arrange
        Customer customer = customerService.getAllCustomers().get(0);
        String accountNumber = "12345";
        double initialDeposit = 100.0;

        // Act
        Account createdAccount = accountService.createAccount(customer, accountNumber, initialDeposit);

        // Assert
        assertNotNull(createdAccount);
        assertEquals(customer, createdAccount.getCustomer());
        assertEquals(accountNumber, createdAccount.getAccountNumber());
        assertEquals(initialDeposit, createdAccount.getBalance());
    }

    @Test
    public void testCreateAccount_DuplicateAccountNumber() {
        // Arrange
        Customer customer = customerService.getAllCustomers().get(0);
        String accountNumber = "12345";
        double initialDeposit1 = 100.0;
        double initialDeposit2 = 200.0;

        // Create an account with the same account number
        accountService.createAccount(customer, accountNumber, initialDeposit1);

        // Act
        Account createdAccount = accountService.createAccount(customer, accountNumber, initialDeposit2);

        // Assert
        assertNull(createdAccount); // Account creation should fail due to duplicate account number
    }

}

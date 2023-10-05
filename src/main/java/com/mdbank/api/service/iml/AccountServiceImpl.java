package com.mdbank.api.service.iml;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.AccountRepository;
import com.mdbank.api.service.AccountService;
import jakarta.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public static final String OK = "OK";
    public static final String INSUFFICIENT_BALANCE_IN_THE_SOURCE_ACCOUNT = "Insufficient balance in the source account.";
    public static final String INVALID_SOURCE_OR_TARGET_ACCOUNT = "Invalid source or target account.";
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(Customer customer, String accountNumber, double initialDeposit) {

        // Check if the account number is already used for the current customer
        if (accountRepository.existsByCustomerAndAccountNumber(customer, accountNumber)) {
            logger.error("Account number is already in use for this customer. Please choose a different account number.");
            return null;
        }

        // If the account number is not in use, proceed to create the account
        try {
            Account bankAccount = new Account();
            bankAccount.setCustomer(customer);
            bankAccount.setAccountNumber(accountNumber);
            bankAccount.setBalance(initialDeposit);
            return accountRepository.save(bankAccount);
        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations (e.g., unique constraint violation)
            logger.error("Error creating account due to database constraint violation: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            logger.error("Unexpected error creating account: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public String transferAmount(Long sourceAccountId, Long targetAccountId, double amount) {
        // Find the source and target accounts
        Account sourceAccount = accountRepository.findById(sourceAccountId).orElse(null);
        Account targetAccount = accountRepository.findById(targetAccountId).orElse(null);

        if (sourceAccount == null || targetAccount == null) {
            logger.info(INVALID_SOURCE_OR_TARGET_ACCOUNT);
            return INVALID_SOURCE_OR_TARGET_ACCOUNT;
        }

        // Check if the source account has sufficient balance
        if (sourceAccount.getBalance() < amount) {
            logger.info(INSUFFICIENT_BALANCE_IN_THE_SOURCE_ACCOUNT);
            return INSUFFICIENT_BALANCE_IN_THE_SOURCE_ACCOUNT;
        }

        // Perform the transfer
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        // Update the accounts in the database
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        logger.info("Transfer successful...");

        return OK;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}

package com.mdbank.api.web.rest;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.dto.AccountDTO;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/accounts")
@Tag(name = "Account API", description = "Account API operations")
public class AccountController {

    private final AccountService accountService;

    private final CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    /**
     * POST /create : Create a new bank account.
     *
     * @param accountDto the account DTO to create
     * @return the ResponseEntity with status 200 (OK) and with body the updated account DTO,
     *         or with status 404 (Not Found) if the customer or account number is not found
     */
    @PostMapping("/create")
    public ResponseEntity<?> createBankAccount(
            @RequestBody @NotNull AccountDTO accountDto) {
        String message = "";
        Customer customer = customerService.getCustomerById(accountDto.getCustomerId());
       // This check is a must because jakarta.validation  @Positive is not working properly
        if (accountDto.getInitialDeposit() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Initial deposit must be greater than 0");
        }

        if (customer != null) {
            Account account = accountService.createAccount(customer, accountDto.getAccountNumber(), accountDto.getInitialDeposit());

        if(account == null) {
            message = String.format("Account number %s is not unique. Please choose a different account number.", accountDto.getAccountNumber());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

            AccountDTO updatedAccountDTO = AccountDTO.builder()
                    .customerId(account.getId())
                    .accountNumber(account.getAccountNumber())
                    .initialDeposit(account.getBalance())
                    .build();

            return ResponseEntity.ok(updatedAccountDTO);
        } else {
            message = String.format("Customer with id %d not found", accountDto.getCustomerId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    /**
     * GET /account/{accountId}/balance : Get the balance of an account by its ID.
     *
     * @param accountId the ID of the account to retrieve the balance for
     * @return the ResponseEntity with status 200 (OK) and the balance of the account in the body, or with status 404 (Not Found) if the account was not found
     */
    @GetMapping("/account/{accountId}/balance")
    public ResponseEntity<?> getAccountBalance(@PathVariable Long accountId) {
        // Retrieve the account by its ID as an Optional
        Optional<Account> optionalAccount = accountService.getAccountById(accountId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            double balance = account.getBalance();
            return ResponseEntity.ok(balance);
        } else {
            String message = String.format("Account with accountId %d not found", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

}





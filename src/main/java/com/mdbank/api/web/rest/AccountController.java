package com.mdbank.api.web.rest;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.dto.AccountDto;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    private final CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createBankAccount(
            @RequestParam Long customerId,
            @RequestParam String accountNumber,
            @RequestParam double initialDeposit) {

        // Get the customer by id
        Customer customer = customerService.getCustomerById(customerId);

        if (customer != null) {
            Account bankAccount = accountService.createAccount(customer, accountNumber, initialDeposit);
            // Map the Account entity to an AccountDto
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountNumber(bankAccount.getAccountNumber());
            accountDto.setBalance(bankAccount.getBalance());
            return ResponseEntity.ok(accountDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.mdbank.api.service.iml;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.AccountRepository;
import com.mdbank.api.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(Customer customer, String accountNumber, double initialDeposit) {
        Account bankAccount = new Account();
        bankAccount.setCustomer(customer);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(initialDeposit);

        return accountRepository.save(bankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getBankAccountById(Long id) {
        return accountRepository.findById(id);
    }
}

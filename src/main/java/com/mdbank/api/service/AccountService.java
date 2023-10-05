package com.mdbank.api.service;


import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;

import java.util.Optional;

public interface AccountService {

    Account createAccount(Customer customer, String accountNumber, double initialDeposit);
    Optional<Account> getBankAccountById(Long id);

}

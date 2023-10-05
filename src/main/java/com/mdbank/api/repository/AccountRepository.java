package com.mdbank.api.repository;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {
    boolean existsByCustomerAndAccountNumber(Customer customer, String accountNumber);
}

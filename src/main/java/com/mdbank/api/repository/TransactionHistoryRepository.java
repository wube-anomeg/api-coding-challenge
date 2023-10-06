package com.mdbank.api.repository;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findBySourceAccountOrTargetAccount(Account sourceAccount, Account targetAccount);
}

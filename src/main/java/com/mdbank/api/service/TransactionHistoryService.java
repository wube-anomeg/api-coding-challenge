package com.mdbank.api.service;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;

import java.util.List;

public interface TransactionHistoryService {
    List<TransactionHistory> getTransactionsForAccount(Account account);
}
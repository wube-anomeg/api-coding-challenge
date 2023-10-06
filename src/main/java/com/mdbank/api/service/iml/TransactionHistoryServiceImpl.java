package com.mdbank.api.service.iml;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.TransactionHistory;
import com.mdbank.api.repository.TransactionHistoryRepository;
import com.mdbank.api.service.TransactionHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Override
    public List<TransactionHistory> getTransactionsForAccount(Account account) {
        return transactionHistoryRepository.findBySourceAccountOrTargetAccount(account, account);
    }
}

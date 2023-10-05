package com.mdbank.api.domain;

import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private double balance;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> transactionsFrom;

    @OneToMany(mappedBy = "targetAccount")
    private List<Transaction> transactionsTo;
}

package com.mdbank.api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true) // Make accountNumber unique
    private String accountNumber;
    private double balance;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount")
    private List<TransactionHistory> transactionsFrom;

    @OneToMany(mappedBy = "targetAccount")
    private List<TransactionHistory> transactionsTo;
}

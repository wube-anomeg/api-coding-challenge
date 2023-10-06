package com.mdbank.api.domain;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account targetAccount;
}

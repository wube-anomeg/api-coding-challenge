package com.mdbank.api.domain;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    private Account sourceAccount;

    @ManyToOne
    private Account targetAccount;
}

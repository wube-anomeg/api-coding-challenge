package com.mdbank.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionHistoryDTO {
    private Long id;
    private Double amount;
    private LocalDateTime timestamp;
    private String sourceAccountNumber;
    private String targetAccountNumber;
}

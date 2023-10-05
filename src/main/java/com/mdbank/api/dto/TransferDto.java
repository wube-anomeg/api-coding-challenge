package com.mdbank.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferDto {
    @NotNull
    @Positive
    private Long sourceAccountId;
    @NotNull
    @Positive
    private Long targetAccountId;
    @NotNull
    @Positive
    private Double amount;
}

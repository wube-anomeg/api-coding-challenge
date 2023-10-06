package com.mdbank.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {
    @NotNull
    @Positive
    private Long customerId;
    
    @NotNull
    private String accountNumber;

    @NotNull
    @Positive
    private Double initialDeposit;

}

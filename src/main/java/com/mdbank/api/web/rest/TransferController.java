package com.mdbank.api.web.rest;

import com.mdbank.api.dto.TransferDto;
import com.mdbank.api.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transfers")
@Tag(name = "Account Transfer API", description = "Account Transfer API operations")
public class TransferController {

    public static final String OK = "OK";
    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(@RequestBody TransferDto transferDto) {
        Long sourceAccountId = transferDto.getSourceAccountId();
        Long targetAccountId = transferDto.getTargetAccountId();
        double amount = transferDto.getAmount();

        if (transferDto.getAmount() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transfer amount must be greater than 0");
        }
        // Validate input and execute the transfer
        String resultMessage = accountService.transferAmount(sourceAccountId, targetAccountId, amount);

        if (resultMessage.equals(OK)) {
            String message = String.format("The amount of $ %s successfully transferred", amount);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(resultMessage);
        }
    }

}

package web.rest;

import com.mdbank.api.dto.TransferDto;
import com.mdbank.api.service.AccountService;
import com.mdbank.api.web.rest.TransferController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferControllerTest {

    @InjectMocks
    private TransferController transferController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferAmount_SuccessfulTransfer() {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccountId(1L);
        transferDto.setTargetAccountId(2L);
        transferDto.setAmount(100.0);

        when(accountService.transferAmount(1L, 2L, 100.0)).thenReturn("OK");

        // Act
        ResponseEntity<String> responseEntity = transferController.transferAmount(transferDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("The amount of $ 100.0 successfully transferred", responseEntity.getBody());

        verify(accountService, times(1)).transferAmount(1L, 2L, 100.0);
    }

    @Test
    public void testTransferAmount_InvalidAmount() {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccountId(1L);
        transferDto.setTargetAccountId(2L);
        transferDto.setAmount(0.0); // Invalid amount

        // Act
        ResponseEntity<String> responseEntity = transferController.transferAmount(transferDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Transfer amount must be greater than 0", responseEntity.getBody());

        verify(accountService, never()).transferAmount(anyLong(), anyLong(), anyDouble());
    }

    @Test
    public void testTransferAmount_FailedTransfer() {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccountId(1L);
        transferDto.setTargetAccountId(2L);
        transferDto.setAmount(100.0);

        when(accountService.transferAmount(1L, 2L, 100.0)).thenReturn("Insufficient balance");

        // Act
        ResponseEntity<String> responseEntity = transferController.transferAmount(transferDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Insufficient balance", responseEntity.getBody());

        verify(accountService, times(1)).transferAmount(1L, 2L, 100.0);
    }
}

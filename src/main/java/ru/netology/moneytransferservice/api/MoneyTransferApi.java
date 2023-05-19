package ru.netology.moneytransferservice.api;

import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.OperationIdDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;

public interface MoneyTransferApi {
    ResponseEntity<OperationIdDto> transfer(TransferDto transferData);

    ResponseEntity<OperationIdDto> confirmOperation(ConfirmDto confirmDTO);
}

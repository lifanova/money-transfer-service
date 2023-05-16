package ru.netology.moneytransferservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.OperationIdDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@RestController
@RequestMapping("/")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<OperationIdDto> transfer(@RequestBody TransferDto transferData) {
        String operationId = moneyTransferService.transfer(transferData);
        OperationIdDto operationIdDto = new OperationIdDto(operationId);

        return ResponseEntity.ok().body(operationIdDto);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationIdDto> confirmOperation(@RequestBody ConfirmDto confirmDTO) {
        String operationId = moneyTransferService.confirmOperation(confirmDTO);
        OperationIdDto operationIdDto = new OperationIdDto(operationId);

        return ResponseEntity.ok().body(operationIdDto);
    }
}

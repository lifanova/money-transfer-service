package ru.netology.moneytransferservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmDto;
import ru.netology.moneytransferservice.model.TransferDto;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@RestController
@RequestMapping("/")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDto transferData) {
        String operationId = moneyTransferService.transfer(transferData);

        return ResponseEntity.ok().body(operationId);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmDto confirmDTO) {
        String operationId = moneyTransferService.confirmOperation(confirmDTO);

        return ResponseEntity.ok().body(operationId);
    }
}

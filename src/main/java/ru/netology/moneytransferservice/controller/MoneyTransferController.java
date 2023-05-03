package ru.netology.moneytransferservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmDTO;
import ru.netology.moneytransferservice.model.TransferDTO;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@RestController
@RequestMapping("/")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO transferData) {
        String operationId = moneyTransferService.transfer(transferData);

        return new ResponseEntity<>(operationId, HttpStatus.OK);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmDTO confirmDTO) {
        String operationId = moneyTransferService.confirmOperation(confirmDTO);

        return new ResponseEntity<>(operationId, HttpStatus.OK);
    }
}

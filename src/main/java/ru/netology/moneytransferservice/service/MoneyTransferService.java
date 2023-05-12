package ru.netology.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.ConfirmDto;
import ru.netology.moneytransferservice.model.TransferDto;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;

import java.util.Random;

@Service
public class MoneyTransferService {
    private MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferDto transferDTO) {
        return moneyTransferRepository.transfer(transferDTO);
    }

    public String confirmOperation(ConfirmDto confirmDTO) {
        return moneyTransferRepository.confirmOperation(confirmDTO);
    }
}

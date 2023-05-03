package ru.netology.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.ConfirmDTO;
import ru.netology.moneytransferservice.model.TransferDTO;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;

@Service
public class MoneyTransferService {
    private MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferDTO transferDTO) {
        return moneyTransferRepository.transfer(transferDTO);
    }

    public String confirmOperation(ConfirmDTO confirmDTO) {
        return moneyTransferRepository.confirmOperation(confirmDTO);
    }
}

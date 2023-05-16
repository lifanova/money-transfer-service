package ru.netology.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;

@Service
public interface MoneyTransferService {
    String transfer(TransferDto transferDTO);

    String confirmOperation(ConfirmDto confirmDTO);
}

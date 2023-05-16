package ru.netology.moneytransferservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@Service
@Slf4j
public class MoneyTransferServiceImpl implements MoneyTransferService {
    private MoneyTransferRepository moneyTransferRepository;
    public MoneyTransferServiceImpl(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferDto transferDTO) {
        log.info("Выполняется перевод суммы {} с карты {} на карту {}", transferDTO.getAmount(), transferDTO.getCardFromNumber(), transferDTO.getCardToNumber());

        return moneyTransferRepository.transfer(transferDTO);
    }

    public String confirmOperation(ConfirmDto confirmDTO) {
        log.info("Выполняется подтверждение операции с id {} ", confirmDTO.getOperationId());

        return moneyTransferRepository.confirmOperation(confirmDTO);
    }
}

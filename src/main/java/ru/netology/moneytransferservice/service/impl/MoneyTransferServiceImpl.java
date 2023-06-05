package ru.netology.moneytransferservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.ErrorTransfer;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.dto.AmountDto;
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

        // из дто получить карту откуда - вытащить ее из репы, если ее нет, то бросить исключение
        Card cardFrom = moneyTransferRepository.findCardByNumber(transferDTO.getCardFromNumber());

        if (cardFrom == null) {
            throw new ErrorTransfer("[transfer]: Карты с таким номером нет");
        }

        // из дто получить карту куда - вытащить ее из репы, если ее нет, то бросить исключение
        Card cardTo = moneyTransferRepository.findCardByNumber(transferDTO.getCardToNumber());

        if (cardTo == null) {
            throw new ErrorTransfer("[transfer]: Карты с таким номером нет");
        }

        // из дто получить сумму - проверить, что она не 0 и что она есть на карте откуда,  то бросить исключение
        AmountDto amount = transferDTO.getAmount();
        if (amount == null || amount.getValue() <= 0) {
            throw new ErrorTransfer("[transfer]: Некорректное значение суммы! ");
        }

        // Проверяем, есть ли на карте указанная сумма
        int allSum = cardFrom.getAmount() - amount.getValue();

        if (allSum < 0) {
            throw new ErrorTransfer("[transfer]: На карте указанной суммы нет!");
        }

        // отдать operationId
        return moneyTransferRepository.transfer(transferDTO);
    }

    public String confirmOperation(ConfirmDto confirmDTO) {
        log.info("Выполняется подтверждение операции с id {} ", confirmDTO.getOperationId());


//        cardFrom.setAmount(allSum);
//        cardTo.setAmount(amount.getValue());

        return moneyTransferRepository.confirmOperation(confirmDTO);
    }
}

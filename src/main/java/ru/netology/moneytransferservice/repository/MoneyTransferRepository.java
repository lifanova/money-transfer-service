package ru.netology.moneytransferservice.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.domain.Operation;
import ru.netology.moneytransferservice.model.dto.AmountDto;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Getter
@Setter
public class MoneyTransferRepository {
    private Map<String, Card> cardMap;

    public MoneyTransferRepository() {
        this.cardMap = new ConcurrentHashMap<>();
        initMap();
    }

    private void initMap() {
        cardMap.put("1234345122772363", new Card("1234345122772363", "12/23", "12400", 1000));
        cardMap.put("5559494000057918", new Card("5559494000057918", "12/23", "123", 0));
    }

    public String transfer(TransferDto transferDTO) {
        Operation operation = new Operation();
        operation.setOperationId(UUID.randomUUID());

        return operation.getOperationId().toString();
    }

    public String confirmOperation(ConfirmDto confirmDTO) {
        return null;
    }

    public Card findCardByNumber(String number) {
        return getCardMap().get(number);
    }


}

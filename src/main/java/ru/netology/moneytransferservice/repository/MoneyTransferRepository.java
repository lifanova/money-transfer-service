package ru.netology.moneytransferservice.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.domain.Account;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.domain.DataOperation;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;

import java.util.Map;
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

    public MoneyTransferRepository(Map<String, Card> cardMap) {
        this.cardMap = cardMap;
    }

    private void initMap() {
        cardMap.put("1234345122772363", new Card("1234345122772363", "12/23", "12400", new Account(10000, "RUR")));
        cardMap.put("5559494000057918", new Card("5559494000057918", "12/23", "123", new Account(10000, "RUR")));
    }

    public static DataOperation acceptData(Card currentCard, TransferDto transferDto) {
        DataOperation dataOperation;
        String cardToNumber = transferDto.getCardToNumber();


        if (currentCard.getNumber().equals(cardToNumber) ||
                !(currentCard.getCvv().equals(transferDto.getCardFromCVV()))
                || !(currentCard.getValidTill().equals(transferDto.getCardFromValidTill()))) {
            throw new ErrorInputData("Ошибка данных карты");
        }

        double currentCardValue = currentCard.getAccount().getValue();
        int transferValue = transferDto.getAmount().getValue();
        double fee = transferValue * 0.01;
        double newValue = currentCardValue - transferValue - fee;

        if (newValue < 0) {
            throw new ErrorInputData("На карте списания недостаточно средств!");
        }

        return new DataOperation(currentCard, cardToNumber, transferValue, newValue, fee);
    }

    public DataOperation transfer(TransferDto transferDTO) {
        for (Map.Entry<String, Card> cardRepoEntry : cardMap.entrySet()) {
            if (transferDTO.getCardFromNumber().equals(cardRepoEntry.getKey())) {
                Card currentCard = cardRepoEntry.getValue();
                return acceptData(currentCard, transferDTO);
            }
        }

        return null;
    }

    public boolean confirmOperation(String operationId, DataOperation dataOperation) {
        String first;
        String second;

        if (operationId != null) {
            String cardFromNumber = dataOperation.getCard().getNumber();
            String cardToNumber = dataOperation.getCardToNumber();

            if (cardToNumber.compareTo(cardFromNumber) < 0) {
                first = cardToNumber;
                second = cardFromNumber;
            } else {
                first = cardFromNumber;
                second = cardToNumber;
            }

            synchronized (first) {
                synchronized (second) {
                    Card currentCard = dataOperation.getCard();
                    double newValueCardFrom = dataOperation.getValue();
                    currentCard.setAccount(new Account(newValueCardFrom, "RUR"));
                    cardMap.put(cardFromNumber, currentCard);

                    if (cardMap.containsKey(cardToNumber)) {
                        Card cardTo = cardMap.get(cardToNumber);
                        double valueCardTo = cardTo.getAccount().getValue();
                        int transferValue = dataOperation.getTransferValue();

                        double newValueCardTo = valueCardTo + transferValue;

                        cardTo.setAccount(new Account(newValueCardTo, "RUR"));
                        cardMap.put(cardToNumber, cardTo);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public Card findCardByNumber(String number) {
        return getCardMap().get(number);
    }


}

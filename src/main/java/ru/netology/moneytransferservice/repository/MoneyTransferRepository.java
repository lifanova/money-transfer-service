package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.AmountDTO;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.ConfirmDTO;
import ru.netology.moneytransferservice.model.TransferDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
public class MoneyTransferRepository {
    public List<Card> cards = Arrays.asList(new Card("1234345122772363", "12/23", "12400", 1000),
            new Card("5559494000057918", "12/23", "123", 0));

    public String transfer(TransferDTO transferDTO) {
        Card cardFrom = findAndCheckCard(transferDTO.getCardFromNumber());

        AmountDTO amount = transferDTO.getAmount();
        if (amount == null || amount.getValue() <= 0) {
            return null;
        }

        // Ищем целевую карту
        Card cardTo = findAndCheckCard(transferDTO.getCardFromNumber());

        // Проверяем, есть ли на карте указанная сумма
        int allSum = cardFrom.getAmount() - amount.getValue();

        if (allSum < 0) {
            System.out.println("На карте указанной суммы нет");
            return null;
        }

        cardFrom.setAmount(allSum);
        cardTo.setAmount(amount.getValue());

        return null;
    }

    public String confirmOperation(ConfirmDTO confirmDTO) {
        return null;
    }

    private Card findAndCheckCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) {
            return null;
        }

        Optional<Card> card = cards.stream().filter(x -> x.getNumber().equals(cardNumber)).findFirst();

        // Ищем карту, с которой переводим
        if (card.isEmpty()) {
            System.out.println("Карты с таким номером нет!");
            return null;
        }

        return card.get();
    }

}

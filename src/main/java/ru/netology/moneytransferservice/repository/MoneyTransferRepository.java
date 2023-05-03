package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.ConfirmDTO;
import ru.netology.moneytransferservice.model.TransferDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MoneyTransferRepository {
    public static Map<String, Card> cardsMap = new ConcurrentHashMap<>();
    public List<Card> cards = Arrays.asList(new Card(), new Card());

    public String transfer(TransferDTO transferDTO){
        return null;
    }

    public String confirmOperation(ConfirmDTO confirmDTO){
        return null;
    }

}

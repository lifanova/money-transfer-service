package ru.netology.moneytransferservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.moneytransferservice.model.domain.Account;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.domain.DataOperation;
import ru.netology.moneytransferservice.model.dto.AmountDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;

import java.util.HashMap;
import java.util.Map;

public class MoneyTransferRepositoryTest {
    // Репозиторий с картами
    private Map<String, Card> cardsStorage;
    // Карта, с которой осуществляем переводы
    private Card cardFrom;
    private Card cardTo;

    private TransferDto transferDto;

    @BeforeEach
    public void initData() {
        // TODO: метод, создающий карту с рандомными значениями
        cardFrom = new Card("5559494000617984",
                "11/23",
                "123",
                new Account(1000000, "RUR"));//testCardValue

        cardTo = new Card("5559494000541093",
                "12/23",
                "456",
                new Account(10000, "RUR"));

        transferDto = new TransferDto(cardFrom.getNumber(), cardFrom.getValidTill(), cardFrom.getCvv(),
                cardTo.getNumber(),
                new AmountDto(1000, "RUR"));

        cardsStorage = new HashMap<>();
        fillMap();
    }

    private void fillMap() {
        cardsStorage.put(cardFrom.getNumber(), cardFrom);
        cardsStorage.put(cardTo.getNumber(), cardTo);
    }

    //
    @Test
    void testAcceptData() {
        int sourceAmount = 2000;
        int transferValue = 1000;
        int fee = 10;
        int newAmount = sourceAmount - transferValue - fee;

        DataOperation expectedDataOperation = new DataOperation(cardFrom, cardTo.getNumber(), transferValue, newAmount, fee);
        DataOperation resultDataOperation = MoneyTransferRepository.acceptData(cardFrom, transferDto);
        // совпадают 2 объекта
        Assertions.assertEquals(expectedDataOperation, resultDataOperation);
    }

    // проводка средств с карты на карту
    @Test
    void testTransferRepository() {
        int sourceAmount = 2000;
        int transferValue = 1000;
        int fee = 10;
        int newAmount = sourceAmount - transferValue - fee;

        DataOperation expectedDataOperation = new DataOperation(cardFrom, cardTo.getNumber(), transferValue, newAmount, fee);
        DataOperation resultDataOperation = new MoneyTransferRepository(cardsStorage)
                .transfer(transferDto);
        Assertions.assertEquals(expectedDataOperation, resultDataOperation);
    }

    // подтверждение операции
    @Test
    void testConfirmOperationRepository() {
        MoneyTransferRepository repository = new MoneyTransferRepository(cardsStorage);
        int sourceAmount = 2000;
        int transferValue = 1000;
        int fee = 10;
        int newAmount = sourceAmount - transferValue - fee;
        DataOperation testDataOperation = new DataOperation(cardFrom, cardTo.getNumber(), transferValue, newAmount, fee);
        String testOperationId = "Bn@Operation#0001";

        boolean resultConfirm = repository.confirmOperation(testOperationId, testDataOperation);
        Assertions.assertTrue(resultConfirm);
    }
}

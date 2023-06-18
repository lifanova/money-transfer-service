package ru.netology.moneytransferservice.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.moneytransferservice.model.domain.Account;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.domain.DataOperation;
import ru.netology.moneytransferservice.model.dto.AmountDto;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;
import ru.netology.moneytransferservice.service.impl.MoneyTransferServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MoneyTransferServiceTest {
    MoneyTransferRepository moneyTransferRepositoryMock = Mockito.mock(MoneyTransferRepository.class);

    Map<String, DataOperation> operationsRepositoryMock = Mockito.mock(Map.class);
    Map<String, String> verificationRepositoryMock = Mockito.mock(Map.class);

    // Репозиторий с картами
    private Map<String, Card> cardsStorage;
    // Карта, с которой осуществляем переводы
    private Card cardFrom;
    private Card cardTo;

    private TransferDto transferDto;
    private DataOperation expectedDataOperation;

    private MoneyTransferService moneyTransferService;

    String testOperationId = "Bn@Operation#0001";
    String testCode = "7777";
    private ConfirmDto confirmDto = new ConfirmDto(testOperationId, testCode);

    @BeforeEach
    public void mockBeforeEach() {
        initData();

        moneyTransferService = new MoneyTransferServiceImpl(moneyTransferRepositoryMock);

        Mockito.when(operationsRepositoryMock.get(testOperationId))
                .thenReturn(expectedDataOperation);
        Mockito.when(verificationRepositoryMock.containsKey(testOperationId))
                .thenReturn(true);
        Mockito.when(verificationRepositoryMock.get(testOperationId))
                .thenReturn(testCode);
    }

    private void initData() {
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

        int sourceAmount = 2000;
        int transferValue = 1000;
        int fee = 10;
        int newAmount = sourceAmount - transferValue - fee;
        expectedDataOperation = new DataOperation(cardFrom, cardTo.getNumber(), transferValue, newAmount, fee);
    }

    private void fillMap() {
        cardsStorage.put(cardFrom.getNumber(), cardFrom);
        cardsStorage.put(cardTo.getNumber(), cardTo);
    }

    @Before
    public void mockTransfer() {
        Mockito.when(moneyTransferRepositoryMock.transfer(transferDto))
                .thenReturn(expectedDataOperation);
    }

    @Test
    void testTransferService() {
        mockTransfer();
        String resultOperationId = moneyTransferService.transfer(transferDto);
        Assertions.assertEquals(testOperationId, resultOperationId);
    }

    @Before
    public void mockConfirm() {
        Mockito.when(moneyTransferRepositoryMock.confirmOperation(testOperationId, expectedDataOperation))
                .thenReturn(true);
    }

    @Test
    void testConfirmOperationService() {
        mockConfirm();
        String resultOperationId = moneyTransferService.confirmOperation(confirmDto);
        String expectedOperationId = "Bn@Operation#0001";
        Assertions.assertEquals(expectedOperationId, resultOperationId);
    }

}

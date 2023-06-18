package ru.netology.moneytransferservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.domain.Account;
import ru.netology.moneytransferservice.model.domain.Card;
import ru.netology.moneytransferservice.model.domain.DataOperation;
import ru.netology.moneytransferservice.model.dto.AmountDto;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.OperationIdDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;
import ru.netology.moneytransferservice.service.MoneyTransferService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransferControllerTest {
    MoneyTransferService moneyTransferServiceMock = Mockito.mock(MoneyTransferService.class);

   // Репозиторий с картами
    private Map<String, Card> cardsStorage;
    // Карта, с которой осуществляем переводы
    private Card cardFrom;
    private Card cardTo;

    private TransferDto transferDto;
    private DataOperation expectedDataOperation;
    private String testOperationId = "Bn@Operation#0001";
    String testCode = "7777";
    private ConfirmDto confirmDto = new ConfirmDto(testOperationId, testCode);

    @BeforeEach
    public void mockBefore() {
        initData();

        Mockito.when(moneyTransferServiceMock.transfer(transferDto))
                .thenReturn(testOperationId);
        Mockito.when(moneyTransferServiceMock.confirmOperation(confirmDto))
                .thenReturn(testOperationId);

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

    @Test
    void testOperationIdTransferController() {
        OperationIdDto resultResponse = new OperationIdDto(moneyTransferServiceMock.transfer(transferDto));
        OperationIdDto expectedResponse = new OperationIdDto(testOperationId);
        Assertions.assertEquals(expectedResponse, resultResponse);
    }

    @Test
    void testOperationIdConfirmController() {
        OperationIdDto expectedResponse = new OperationIdDto(testOperationId);
        OperationIdDto resultResponse = new OperationIdDto(moneyTransferServiceMock.confirmOperation(confirmDto));
        Assertions.assertEquals(expectedResponse, resultResponse);
    }


    @Test
    void testTransferController() {
        ResponseEntity<OperationIdDto> result = new MoneyTransferController(moneyTransferServiceMock).transfer(transferDto);
        ResponseEntity<OperationIdDto> expected = new ResponseEntity<>((new OperationIdDto(testOperationId)), HttpStatus.OK);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testConfirmController() {
        ResponseEntity<OperationIdDto> expected = new ResponseEntity<>((new OperationIdDto(testOperationId)), HttpStatus.OK);
        ResponseEntity<OperationIdDto> result = new MoneyTransferController(moneyTransferServiceMock).confirmOperation(confirmDto);
        Assertions.assertEquals(expected, result);
    }
}

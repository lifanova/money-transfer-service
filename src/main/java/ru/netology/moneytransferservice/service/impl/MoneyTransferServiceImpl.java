package ru.netology.moneytransferservice.service.impl;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.ErrorConfirmation;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.model.domain.DataOperation;
import ru.netology.moneytransferservice.model.dto.ConfirmDto;
import ru.netology.moneytransferservice.model.dto.TransferDto;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;
import ru.netology.moneytransferservice.service.MoneyTransferService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MoneyTransferServiceImpl implements MoneyTransferService {
    private MoneyTransferRepository moneyTransferRepository;

    public Map<String, DataOperation> operationsStorage = new ConcurrentHashMap<>();
    private final AtomicInteger idNumber = new AtomicInteger(1);
    public Map<String, String> confirmStorage = new ConcurrentHashMap<>();

    public MoneyTransferServiceImpl(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferDto transferData) {
        String operationId;
        String code = generateCode();
        String logData = "Ошибка ввода данных карты";
        String logTime = "Срок действия вашей карты истёк";

        String cardValidTill = transferData.getCardFromValidTill();

        if (validateCardDate(cardValidTill)) {
            DataOperation dataNewOperation = moneyTransferRepository.transfer(transferData);

            if (dataNewOperation != null) {
                operationId = "Bn@Operation#000" + idNumber.getAndIncrement();
                operationsStorage.put(operationId, dataNewOperation);
                confirmStorage.put(operationId, code);

                sendCodeToPhone(code);
            } else {
                throw new ErrorInputData(logData);
            }
        } else {
            throw new ErrorInputData(logTime);
        }

        return operationId;
    }

    public String confirmOperation(ConfirmDto confirmDto) {
        String operationId = confirmDto.getOperationId();
        String logCode = "Неверный код подтверждения";
        String logId = "Транзакция отклонена!";

        if (confirmStorage.containsKey(operationId) && operationId != null) {
            String code = confirmStorage.get(operationId);

            if (code != null && isCodeCorrect(code)) {
                DataOperation currentDataOperation = operationsStorage.get(operationId);
                if (moneyTransferRepository.confirmOperation(operationId, currentDataOperation)) {
                    System.out.println("Транзакция подтверждена!");
                } else {
                    System.out.println(logId);
                    throw new ErrorConfirmation(logId);
                }
            } else {
                System.out.println(logCode);
                throw new ErrorConfirmation(logCode);
            }
        } else {
            System.out.println(logId);
            throw new ErrorConfirmation(logId);
        }
        return operationId;
    }

    public static String generateCode() {
        Random random = new Random();
        int codeInt = random.nextInt(8999) + 1000;
        return String.valueOf(codeInt);
    }

    public static String sendCodeToPhone(String code) {
        String msg = "Клиенту на телефон отправлен код подтвержения транзакции: " + code;
        System.out.println(msg);
        return msg;
    }

    // Эмуляция верификации:
    // если случайный код
    // меньше или равен 5000,
    // мы считаем, что клиент
    // ввёл неверный пин-код.
    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 5000);
    }

    public static boolean validateCardDate(String cardValdTill) {
        Date cardDate = null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM/yy");
        try {
            cardDate = format.parse(cardValdTill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if (cardDate != null) {
            long diffDate = cardDate.getTime() - todaysDate.getTime();
            int month = Integer.parseInt(cardValdTill.substring(0, 2));

            return ((diffDate >= 0) && (month > 0) && (month < 13));
        }
        return false;
    }

}

package ru.netology.moneytransferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.exception.ErrorConfirmation;
import ru.netology.moneytransferservice.exception.ErrorInputData;
import ru.netology.moneytransferservice.exception.ErrorTransfer;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final String INPUT_MSG = "Ошибка ввода данных карты";
    private static final String TRANSFER_MSG = "Ошибка перевода";
    private static final String CONFIRM_MSG = "Ошибка подтверждения";

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> handleErrorInputData(ErrorInputData e) {
        return new ResponseEntity<>(String.format("%s: %s", INPUT_MSG, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<String> handleErrorTransfer(ErrorTransfer e) {
        return new ResponseEntity<>(String.format("%s: %s", TRANSFER_MSG, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<String> handleErrorConfirmation(ErrorConfirmation e) {
        return new ResponseEntity<>(String.format("%s: %s", CONFIRM_MSG, e.getMessage()), HttpStatus.NOT_FOUND);
    }

}

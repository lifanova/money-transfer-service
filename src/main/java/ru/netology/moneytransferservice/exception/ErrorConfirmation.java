package ru.netology.moneytransferservice.exception;

public class ErrorConfirmation extends RuntimeException {
    public ErrorConfirmation(String message) {
        super(message);
    }
}

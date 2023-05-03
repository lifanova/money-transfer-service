package ru.netology.moneytransferservice.exception;

public class ErrorTransfer extends RuntimeException {
    public ErrorTransfer(String message) {
        super(message);
    }
}

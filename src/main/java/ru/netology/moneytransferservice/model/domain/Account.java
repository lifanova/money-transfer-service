package ru.netology.moneytransferservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Счет, к которому привязана карта, храним баланс (суммсу) и валюту

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    // возможно, здесь BigDecimal?
    private double value;
    private String currency;
}

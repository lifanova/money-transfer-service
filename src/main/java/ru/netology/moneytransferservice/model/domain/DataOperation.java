package ru.netology.moneytransferservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataOperation {
    private Card card;
    private String cardToNumber;
    private int transferValue;
    private double value;
    private double fee;

}

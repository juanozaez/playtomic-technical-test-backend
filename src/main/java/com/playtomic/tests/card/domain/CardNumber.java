package com.playtomic.tests.card.domain;

import lombok.Data;

@Data
public class CardNumber {

    public CardNumber(String value) {
        this.value = value;
    }

    private String value;
}

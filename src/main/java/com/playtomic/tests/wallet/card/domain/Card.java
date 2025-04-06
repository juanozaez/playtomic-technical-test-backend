package com.playtomic.tests.wallet.card.domain;

import lombok.Data;

@Data
public class Card {

    public Card(CardNumber number) {
        this.number = number;
    }

    public String number() {
        return number.getValue();
    }

    private CardNumber number;
}


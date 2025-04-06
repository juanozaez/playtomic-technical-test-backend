package com.playtomic.tests.card.domain;

public record Card(CardNumber number) {

    public String numberAsString(){
        return number.value();
    }
}


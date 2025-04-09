package com.playtomic.tests.card.domain;

import com.playtomic.tests.card.domain.error.InvalidCardError;
import lombok.NonNull;

public record Card(@NonNull CardNumber number) {

    public Card {
        if (number.value().isBlank()) {
            throw new InvalidCardError();
        }
        // TODO add extra card validations
    }

    public String numberAsString() {
        return number.value();
    }
}


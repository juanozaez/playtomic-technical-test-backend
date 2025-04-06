package com.playtomic.tests.card.mother;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.card.domain.CardNumber;

public class CardMother {

    public static Card valid() {
        return new Card(new CardNumber("4111111111111111"));
    }
}

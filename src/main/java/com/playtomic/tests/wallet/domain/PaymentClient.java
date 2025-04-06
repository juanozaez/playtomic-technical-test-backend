package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.card.domain.Card;
import java.math.BigDecimal;
import lombok.NonNull;

public interface PaymentClient {
    String charge(@NonNull Card card, @NonNull BigDecimal amount);

    void refund(@NonNull String paymentId);
}

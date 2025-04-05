package com.playtomic.tests.wallet.wallet.domain;

import java.math.BigDecimal;
import lombok.NonNull;

public interface PaymentClient {
    String charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount);

    void refund(@NonNull String paymentId);
}

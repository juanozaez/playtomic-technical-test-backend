package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public record Balance(BigDecimal amount) {
    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }
}

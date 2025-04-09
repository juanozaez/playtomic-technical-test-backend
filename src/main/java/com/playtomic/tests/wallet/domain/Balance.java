package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public record Balance(BigDecimal amount) {
    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }

    public Balance add(BigDecimal amount) {
        return new Balance(this.amount.add(amount));
    }
}

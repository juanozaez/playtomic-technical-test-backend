package com.playtomic.tests.wallet.wallet.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Balance {
    public BigDecimal amount;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }
}

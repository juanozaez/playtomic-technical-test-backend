package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Transaction {
    private String paymentId;
    private BigDecimal amount;

    public Transaction(String paymentId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }
}

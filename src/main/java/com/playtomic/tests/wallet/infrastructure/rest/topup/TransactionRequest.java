package com.playtomic.tests.wallet.infrastructure.rest.topup;

import java.math.BigDecimal;

public record TransactionRequest(String transactionId,
                                 BigDecimal amount,
                                 CreditCard creditCard) {

    public record CreditCard(String cardNumber) {
    }
}

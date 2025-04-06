package com.playtomic.tests.wallet.infrastructure.rest.topup;

import java.math.BigDecimal;

public record TransactionRequest(String transactionId,
                                 BigDecimal amount,
                                 CreditCard creditCard) {

    public static record CreditCard(String cardNumber) {
    }
}

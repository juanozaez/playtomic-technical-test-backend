package com.playtomic.tests.wallet.wallet.infrastructure.rest.topup;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequest {
    private String transactionId;
    private BigDecimal amount;
    private CreditCard creditCard;

    @Data
    public static class CreditCard {
        private String cardNumber;
    }
}

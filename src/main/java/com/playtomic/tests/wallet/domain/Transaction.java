package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public record Transaction(TransactionId id, BigDecimal amount) {
}

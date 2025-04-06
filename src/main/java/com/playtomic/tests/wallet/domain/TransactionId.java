package com.playtomic.tests.wallet.domain;

import java.util.UUID;

public record TransactionId(UUID value) {

    public static TransactionId fromString(String id) {
        return new TransactionId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

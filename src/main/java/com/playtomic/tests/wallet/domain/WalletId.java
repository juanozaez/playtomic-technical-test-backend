package com.playtomic.tests.wallet.domain;

import java.util.UUID;

public record WalletId(UUID value) {
    public static WalletId fromString(String id) {
        return new WalletId(UUID.fromString(id));
    }
}

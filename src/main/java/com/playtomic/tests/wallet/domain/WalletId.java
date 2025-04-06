package com.playtomic.tests.wallet.domain;

import java.util.UUID;
import lombok.Getter;

public class WalletId {

    public WalletId(UUID value) {
        this.value = value;
    }

    public static WalletId fromString(String id) {
        return new WalletId(UUID.fromString(id));
    }

    @Getter
    private UUID value;
}

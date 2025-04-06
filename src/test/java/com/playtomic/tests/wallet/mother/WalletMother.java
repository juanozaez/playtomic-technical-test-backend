package com.playtomic.tests.wallet.mother;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletMother {

    public static Wallet emptyWallet() {
        return Wallet.emptyWallet(new WalletId(UUID.randomUUID()));
    }

    public static Wallet positiveWallet() {
        return Wallet.emptyWallet(new WalletId(UUID.randomUUID()))
                .topUp(new BigDecimal(70));
    }
}

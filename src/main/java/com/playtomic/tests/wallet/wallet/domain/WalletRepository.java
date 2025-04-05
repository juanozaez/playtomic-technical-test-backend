package com.playtomic.tests.wallet.wallet.domain;

public interface WalletRepository {
    Wallet findById(WalletId walletId);
    Wallet findForUpdateById(WalletId walletId);
    void save(Wallet wallet);
}

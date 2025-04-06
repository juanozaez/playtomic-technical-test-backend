package com.playtomic.tests.wallet.domain;

public interface WalletRepository {
    Wallet findById(WalletId walletId);
    Wallet findByIdLocking(WalletId walletId);
    void save(Wallet wallet);
}

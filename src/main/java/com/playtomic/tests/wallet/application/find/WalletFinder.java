package com.playtomic.tests.wallet.application.find;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import com.playtomic.tests.wallet.domain.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletFinder {
    private final WalletRepository walletRepository;

    public WalletFinder(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet findById(WalletId walletId) {
        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new WalletNotFoundError();
        }

        return wallet;
    }
}

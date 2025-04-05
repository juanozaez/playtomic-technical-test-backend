package com.playtomic.tests.wallet.wallet.application.find;

import com.playtomic.tests.wallet.wallet.domain.Wallet;
import com.playtomic.tests.wallet.wallet.domain.WalletId;
import com.playtomic.tests.wallet.wallet.domain.error.WalletNotFoundError;
import com.playtomic.tests.wallet.wallet.domain.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletFinder {
    private WalletRepository walletRepository;

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

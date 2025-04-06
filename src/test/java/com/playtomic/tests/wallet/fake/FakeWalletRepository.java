package com.playtomic.tests.wallet.fake;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import java.util.ArrayList;
import java.util.List;

public class FakeWalletRepository implements WalletRepository {
    private List<Wallet> wallets = new ArrayList<>();

    public void reset() {
        wallets.clear();
    }

    @Override
    public Wallet findById(WalletId walletId) {
        return wallets.stream()
                .filter(wallet -> wallet.id().equals(walletId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Wallet findByIdLocking(WalletId walletId) {
        return findById(walletId);
    }

    @Override
    public void save(Wallet wallet) {
        Wallet existingWallet = findById(wallet.id());
        if (existingWallet != null) {
            wallets.remove(existingWallet);
        }
        wallets.add(wallet);

    }
}

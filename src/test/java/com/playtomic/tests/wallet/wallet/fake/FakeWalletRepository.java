package com.playtomic.tests.wallet.wallet.fake;

import com.playtomic.tests.wallet.wallet.domain.Wallet;
import com.playtomic.tests.wallet.wallet.domain.WalletId;
import com.playtomic.tests.wallet.wallet.domain.WalletRepository;
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
                .filter(wallet -> wallet.getId().equals(walletId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Wallet findForUpdateById(WalletId walletId) {
        return findById(walletId);
    }

    @Override
    public void save(Wallet wallet) {
        Wallet existingWallet = findById(wallet.getId());
        if (existingWallet != null) {
            wallets.remove(existingWallet);
        }
        wallets.add(wallet);

    }
}

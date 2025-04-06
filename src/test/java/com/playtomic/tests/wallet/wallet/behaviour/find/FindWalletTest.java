package com.playtomic.tests.wallet.wallet.behaviour.find;

import com.playtomic.tests.wallet.wallet.application.find.WalletFinder;
import com.playtomic.tests.wallet.wallet.domain.Wallet;
import com.playtomic.tests.wallet.wallet.domain.error.WalletNotFoundError;
import com.playtomic.tests.wallet.wallet.fake.FakeWalletRepository;
import com.playtomic.tests.wallet.wallet.mother.WalletMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindWalletTest {

    private final FakeWalletRepository repository = new FakeWalletRepository();
    private final WalletFinder finder = new WalletFinder(repository);

    @BeforeEach
    public void setUp() {
        repository.reset();
    }

    @Test
    public void findsWallet() {
        repository.save(wallet);

        Wallet result = finder.findById(wallet.getId());

        assert result.equals(wallet);
    }

    @Test
    public void returnsErrorIfWalletNotFound() {
        assertThrows(WalletNotFoundError.class, () -> finder.findById(wallet.getId()));
    }

    private final Wallet wallet = WalletMother.positiveWallet();
}

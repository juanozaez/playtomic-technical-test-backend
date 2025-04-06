package com.playtomic.tests.wallet.integration.persistence;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.infrastructure.persistence.JpaWalletRepository;
import com.playtomic.tests.wallet.mother.WalletMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class JpaWalletRepositoryTest {
    @Autowired
    private JpaWalletRepository repo;

    @Test
    @Transactional
    void saves_and_finds_wallet() {
        repo.save(wallet);

        Wallet result = repo.findById(wallet.id());

        assert result.equals(wallet);
    }

    @Test
    @Transactional
    void finds_wallet_for_update() {
        // TODO to improve: proper assertion to verify a lock is taking place
    }

    private final Wallet wallet = WalletMother.positiveWallet();
}

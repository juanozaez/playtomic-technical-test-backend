package com.playtomic.tests.wallet.infrastructure.persistence;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaWalletRepository implements WalletRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Wallet findById(WalletId walletId) {
        return entityManager.find(Wallet.class, walletId);
    }

    @Override
    public Wallet findForUpdateById(WalletId walletId) {
        return entityManager.find(Wallet.class, walletId, LockModeType.PESSIMISTIC_WRITE);

    }

    @Transactional
    @Override
    public void save(Wallet wallet) {
        entityManager.persist(wallet);
    }
}

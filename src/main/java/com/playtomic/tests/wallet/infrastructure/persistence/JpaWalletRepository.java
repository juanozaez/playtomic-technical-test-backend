package com.playtomic.tests.wallet.infrastructure.persistence;

import com.playtomic.tests.wallet.domain.Balance;
import com.playtomic.tests.wallet.domain.Transaction;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaWalletRepository implements WalletRepository {

    private final InternalJpaWalletRepository repository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    JpaWalletRepository(EntityManager entityManager, InternalJpaWalletRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    @Override
    public Wallet findById(WalletId walletId) {
        return toEntity(repository.findById(walletId.getValue().toString()).get());
    }

    @Transactional
    @Override
    public Wallet findForUpdateById(WalletId walletId) {
        return toEntity(entityManager.find(JpaWallet.class, walletId.getValue().toString(), LockModeType.PESSIMISTIC_READ));
    }

    @Override
    public void save(Wallet wallet) {
        repository.save(toJpa(wallet));
    }

    private Wallet toEntity(JpaWallet jpaWallet) {
        return new Wallet(WalletId.fromString(jpaWallet.getId()),
                new Balance(jpaWallet.getBalance()),
                jpaWallet.getTransactions().stream().map(transaction -> new Transaction(transaction.getPaymentId(), transaction.getAmount()))
                        .toList());
    }

    private JpaWallet toJpa(Wallet wallet) {
        return new JpaWallet(wallet.getId().getValue().toString(), wallet.balance().amount,
                wallet.transactions().stream().map(transaction -> new JpaTransaction(transaction.getPaymentId(), transaction.getAmount()))
                        .toList());
    }
}

@Repository
interface InternalJpaWalletRepository extends JpaRepository<JpaWallet, String> {
}
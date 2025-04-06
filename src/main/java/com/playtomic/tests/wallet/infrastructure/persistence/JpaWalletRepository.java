package com.playtomic.tests.wallet.infrastructure.persistence;

import com.playtomic.tests.wallet.domain.Balance;
import com.playtomic.tests.wallet.domain.Transaction;
import com.playtomic.tests.wallet.domain.TransactionId;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
        return repository.findById(walletId.value().toString())
                .map(this::toEntity)
                .orElse(null);
    }

    @Override
    public Wallet findByIdLocking(WalletId walletId) {
        return repository.findForUpdate(walletId.value().toString())
                .map(this::toEntity)
                .orElse(null);
    }

    @Override
    public void save(Wallet wallet) {
        repository.save(toJpa(wallet));
    }

    private Wallet toEntity(JpaWallet jpaWallet) {
        return new Wallet(WalletId.fromString(jpaWallet.getId()),
                new Balance(jpaWallet.getBalance()),
                jpaWallet
                        .getTransactions().stream()
                        .map(transaction -> new Transaction(TransactionId.fromString(transaction.getId()), transaction.getAmount()))
                        .toList());
    }

    private JpaWallet toJpa(Wallet wallet) {
        return new JpaWallet(wallet.id().value().toString(), wallet.balance().amount(),
                wallet
                        .transactions()
                        .stream()
                        .map(transaction -> new JpaTransaction(transaction.id().toString(), transaction.amount()))
                        .toList());
    }
}

@Repository
interface InternalJpaWalletRepository extends JpaRepository<JpaWallet, String> {
    @Query(value = "SELECT * FROM WALLET WHERE id = :id FOR UPDATE", nativeQuery = true)
    Optional<JpaWallet> findForUpdate(String id);

}
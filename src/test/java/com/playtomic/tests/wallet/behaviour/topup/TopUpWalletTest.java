package com.playtomic.tests.wallet.behaviour.topup;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.card.mother.CardMother;
import com.playtomic.tests.wallet.application.topup.WalletTopUpMaker;
import com.playtomic.tests.wallet.domain.Balance;
import com.playtomic.tests.wallet.domain.Transaction;
import com.playtomic.tests.wallet.domain.TransactionId;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.error.ExistingTransactionError;
import com.playtomic.tests.wallet.domain.error.NegativeAmountError;
import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import com.playtomic.tests.wallet.fake.FakePaymentClient;
import com.playtomic.tests.wallet.fake.FakeWalletRepository;
import com.playtomic.tests.wallet.mother.WalletMother;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TopUpWalletTest {

    private final FakeWalletRepository repository = new FakeWalletRepository();
    private final FakePaymentClient paymentClient = new FakePaymentClient();
    private final WalletTopUpMaker topUpMaker = new WalletTopUpMaker(repository, paymentClient);

    @BeforeEach
    public void setUp() {
        repository.reset();
        paymentClient.reset();
    }

    @Test
    public void tops_up_wallet() {
        walletExists();

        topUpMaker.topUp(wallet.id(), amount, card, transactionId);

        Wallet finalWallet = repository.findById(wallet.id());
        assert finalWallet.balance().equals(new Balance(amount).add(wallet.balance().amount()));
        assert finalWallet.transactions().contains(new Transaction(transactionId, amount));
        assert paymentClient.chargeMade(amount);
    }

    @Test
    public void returns_error_if_wallet_not_found() {
        assertThrows(WalletNotFoundError.class, () -> topUpMaker.topUp(wallet.id(), amount, card, transactionId));
    }

    @Test
    public void returns_error_if_amount_negative() {
        walletExists();

        assertThrows(NegativeAmountError.class, () -> topUpMaker.topUp(wallet.id(), BigDecimal.valueOf(-1), card, transactionId));
    }

    @Test
    public void returns_error_if_transaction_already_exists() {
        walletExists();

        assertThrows(ExistingTransactionError.class, () -> topUpMaker.topUp(wallet.id(), amount, card, wallet.transactions().stream().findFirst().get().id()));
    }

    private void walletExists() {
        repository.save(wallet);
    }

    private final Wallet wallet = WalletMother.positiveWallet();
    private final BigDecimal amount = new BigDecimal("11.30");
    private final Card card = CardMother.valid();
    private final TransactionId transactionId = new TransactionId(UUID.randomUUID());
}

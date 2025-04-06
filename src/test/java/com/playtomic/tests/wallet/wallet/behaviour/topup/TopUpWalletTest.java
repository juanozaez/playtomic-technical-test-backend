package com.playtomic.tests.wallet.wallet.behaviour.topup;

import com.playtomic.tests.wallet.card.domain.Card;
import com.playtomic.tests.wallet.card.mother.CardMother;
import com.playtomic.tests.wallet.wallet.application.topup.WalletTopUpMaker;
import com.playtomic.tests.wallet.wallet.domain.Balance;
import com.playtomic.tests.wallet.wallet.domain.Wallet;
import com.playtomic.tests.wallet.wallet.domain.error.NegativeAmountError;
import com.playtomic.tests.wallet.wallet.domain.error.WalletNotFoundError;
import com.playtomic.tests.wallet.wallet.fake.FakePaymentClient;
import com.playtomic.tests.wallet.wallet.fake.FakeWalletRepository;
import com.playtomic.tests.wallet.wallet.mother.WalletMother;
import java.math.BigDecimal;
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
    public void topsUpWallet() {
        walletExists();

        topUpMaker.topUp(wallet.getId(), amount, card);

        Wallet finalWallet = repository.findById(wallet.getId());
        assert finalWallet.balance().equals(new Balance(amount));
        assert paymentClient.chargeMade(amount);
    }

    @Test
    public void returnsErrorIfWalletNotFound() {
        assertThrows(WalletNotFoundError.class, () -> topUpMaker.topUp(wallet.getId(), amount, card));
    }

    @Test
    public void returnsErrorIfAmountNegative() {
        walletExists();

        assertThrows(NegativeAmountError.class, () -> topUpMaker.topUp(wallet.getId(), BigDecimal.valueOf(-1), card));
    }

    private void walletExists() {
        repository.save(wallet);
    }

    private final Wallet wallet = WalletMother.emptyWallet();
    private final BigDecimal amount = new BigDecimal("11.30");
    private final Card card = CardMother.valid();
}

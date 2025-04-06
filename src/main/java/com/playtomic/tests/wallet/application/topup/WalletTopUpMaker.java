package com.playtomic.tests.wallet.application.topup;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.wallet.domain.PaymentClient;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletTopUpMaker {
    private final WalletRepository walletRepository;
    private final PaymentClient paymentClient;

    public WalletTopUpMaker(WalletRepository walletRepository, PaymentClient paymentClient) {
        this.walletRepository = walletRepository;
        this.paymentClient = paymentClient;
    }

    @Transactional
    public void topUp(WalletId walletId, BigDecimal amount, Card card) {
        Wallet wallet = walletRepository.findForUpdateById(walletId);
        if (wallet == null) {
            throw new WalletNotFoundError();
        }

        Wallet updatedWallet = wallet.topUp(amount);
        paymentClient.charge(card, amount);
        walletRepository.save(updatedWallet);
    }
}

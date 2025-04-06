package com.playtomic.tests.wallet.application.topup;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.wallet.domain.PaymentClient;
import com.playtomic.tests.wallet.domain.TransactionId;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import java.math.BigDecimal;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WalletTopUpMaker {
    private final WalletRepository walletRepository;
    private final PaymentClient paymentClient;

    public WalletTopUpMaker(WalletRepository walletRepository, PaymentClient paymentClient) {
        this.walletRepository = walletRepository;
        this.paymentClient = paymentClient;
    }

    public void topUp(@NonNull WalletId walletId, @NonNull BigDecimal amount, @NonNull Card card, @NonNull TransactionId transactionId) {
        Wallet wallet = walletRepository.findByIdLocking(walletId);
        if (wallet == null) {
            throw new WalletNotFoundError();
        }

        Wallet updatedWallet = wallet.topUp(amount, transactionId);
        paymentClient.charge(card, amount);
        walletRepository.save(updatedWallet);
    }
}

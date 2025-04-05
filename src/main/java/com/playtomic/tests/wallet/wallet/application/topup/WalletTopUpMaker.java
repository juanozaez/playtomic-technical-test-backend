package com.playtomic.tests.wallet.wallet.application.topup;

import com.playtomic.tests.wallet.wallet.domain.PaymentClient;
import com.playtomic.tests.wallet.wallet.domain.Wallet;
import com.playtomic.tests.wallet.wallet.domain.WalletId;
import com.playtomic.tests.wallet.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.wallet.domain.error.WalletNotFoundError;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class WalletTopUpMaker {
    private final WalletRepository walletRepository;
    private final PaymentClient paymentClient;

    public WalletTopUpMaker(WalletRepository walletRepository, PaymentClient paymentClient) {
        this.walletRepository = walletRepository;
        this.paymentClient = paymentClient;
    }

    public void topUp(WalletId walletId, BigDecimal amount, String creditCardNumber) {
        Wallet wallet = walletRepository.findForUpdateById(walletId);
        if (wallet == null) {
            throw new WalletNotFoundError();
        }

        paymentClient.charge(creditCardNumber, amount);
        wallet.topUp(amount);
        walletRepository.save(wallet);
    }
}

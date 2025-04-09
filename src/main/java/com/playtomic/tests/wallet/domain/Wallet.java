package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.wallet.domain.error.NegativeAmountError;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Wallet(WalletId id, Balance balance, List<Transaction> transactions) {

    public static Wallet emptyWallet(WalletId id) {
        return new Wallet(id, Balance.zero(), Collections.emptyList());
    }

    public Wallet topUp(BigDecimal amount, TransactionId transactionId) {
        guardPositiveAmount(amount);

        List<Transaction> updatedTransactions = new ArrayList<>(transactions);
        updatedTransactions.add(new Transaction(transactionId, amount));

        return new Wallet(id, balance.add(amount), updatedTransactions);
    }

    private void guardPositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountError();
        }
    }
}


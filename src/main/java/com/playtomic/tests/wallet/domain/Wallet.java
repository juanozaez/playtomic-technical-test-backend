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
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountError();
        }
        Transaction newTransaction = new Transaction(transactionId, amount);

        List<Transaction> updatedTransactions = new ArrayList<>(this.transactions);
        updatedTransactions.add(newTransaction);
        return new Wallet(this.id, new Balance(this.balance.amount.add(amount)), updatedTransactions);
    }
}


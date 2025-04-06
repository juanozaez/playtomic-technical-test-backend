package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.wallet.domain.error.InsufficientWalletBalanceError;
import com.playtomic.tests.wallet.domain.error.NegativeAmountError;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;

public class Wallet {

    @Getter
    private WalletId id;
    private Balance balance;
    private List<Transaction> transactions;

    private Wallet(WalletId id, Balance balance, List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public static Wallet emptyWallet(WalletId id) {
        return new Wallet(id, Balance.zero(), Collections.emptyList());
    }

    public Wallet topUp(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountError();
        }
        return new Wallet(this.id, new Balance(this.balance.amount.add(amount)), this.transactions);
    }

    public Wallet charge(BigDecimal amount) {
        if (balance.amount.compareTo(amount) < 0) {
            throw new InsufficientWalletBalanceError();
        }

        return new Wallet(
                this.id,
                new Balance(this.balance.amount.subtract(amount)),
                Stream.concat(this.transactions.stream(), Stream.of(new Transaction("Spend", amount))).toList()
        );
    }


    public Balance balance() {
        return this.balance;
    }
}

class Transaction {
    private String paymentId;
    private BigDecimal amount;

    public Transaction(String paymentId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }
}


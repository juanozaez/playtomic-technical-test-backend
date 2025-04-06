package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.wallet.domain.error.NegativeAmountError;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class Wallet {

    @Getter
    private WalletId id;
    private Balance balance;
    private List<Transaction> transactions;

    public Wallet(WalletId id, Balance balance, List<Transaction> transactions) {
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
        // TODO Not implemented but to be discussed
        return this;
    }

    public Wallet refund(String transactionId) {
        // TODO Not implemented but to be discussed
        return this;
    }

    public Balance balance() {
        return this.balance;
    }

    public List<Transaction> transactions() {
        return this.transactions;
    }
}


package com.playtomic.tests.wallet.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "WALLET")
public class JpaWallet {

    @Id
    private String id;

    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wallet_id")
    private List<JpaTransaction> transactions = new ArrayList<>();

    public JpaWallet(String id, BigDecimal balance, List<JpaTransaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
    }
}


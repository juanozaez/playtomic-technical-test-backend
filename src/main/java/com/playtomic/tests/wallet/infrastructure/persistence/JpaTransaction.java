package com.playtomic.tests.wallet.infrastructure.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TRANSACTION")
public class JpaTransaction {

    @Id
    private String id;
    @Column
    private BigDecimal amount;

    public JpaTransaction(String id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }
}

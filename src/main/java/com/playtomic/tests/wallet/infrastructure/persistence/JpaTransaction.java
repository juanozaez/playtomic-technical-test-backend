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
    private Long id;
    @Column
    private String paymentId;
    @Column
    private BigDecimal amount;

    public JpaTransaction(String paymentId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }
}

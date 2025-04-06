package com.playtomic.tests.wallet.infrastructure.rest.find;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FindWalletResponse {
    public FindWalletResponse(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public String id;
    public BigDecimal balance;
}

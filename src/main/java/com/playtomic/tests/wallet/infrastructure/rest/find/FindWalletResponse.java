package com.playtomic.tests.wallet.infrastructure.rest.find;

import java.math.BigDecimal;

public record FindWalletResponse(String id, BigDecimal balance) {
}

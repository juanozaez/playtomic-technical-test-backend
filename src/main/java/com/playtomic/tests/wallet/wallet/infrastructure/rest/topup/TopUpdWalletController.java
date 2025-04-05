package com.playtomic.tests.wallet.wallet.infrastructure.rest.topup;

import com.playtomic.tests.wallet.wallet.application.topup.WalletTopUpMaker;
import com.playtomic.tests.wallet.wallet.domain.WalletId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopUpdWalletController {

    private final WalletTopUpMaker maker;

    public TopUpdWalletController(WalletTopUpMaker maker) {
        this.maker = maker;
    }

    @PostMapping("/wallets/{id}/transactions")
    public void topUpWallet(@PathVariable String id,
                            @RequestBody TransactionRequest transactionRequest) {
        maker.topUp(WalletId.fromString(id), transactionRequest.getAmount(), transactionRequest.getCreditCard().getCardNumber());
    }
}
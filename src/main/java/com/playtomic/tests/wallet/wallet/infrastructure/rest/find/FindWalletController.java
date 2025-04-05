package com.playtomic.tests.wallet.wallet.infrastructure.rest.find;

import com.playtomic.tests.wallet.wallet.application.find.WalletFinder;
import com.playtomic.tests.wallet.wallet.domain.WalletId;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindWalletController {

    private WalletFinder finder;

    public FindWalletController(WalletFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/wallets/{id}")
    void find(@RequestParam String walletId) {
        finder.findById(new WalletId(UUID.fromString(walletId)));
    }
}

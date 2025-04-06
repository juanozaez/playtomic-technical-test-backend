package com.playtomic.tests.wallet.infrastructure.rest.find;

import com.playtomic.tests.wallet.application.find.WalletFinder;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindWalletController {

    private WalletFinder finder;

    public FindWalletController(WalletFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/wallets/{id}")
    FindWalletResponse find(@PathVariable String id) {
        Wallet wallet = finder.findById(new WalletId(UUID.fromString(id)));
        return new FindWalletResponse(wallet.id().value().toString(), wallet.balance().amount());
    }

}

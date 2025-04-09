package com.playtomic.tests;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Named
public class SampleWalletCreator {

    @Inject
    private WalletRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    void init(ApplicationReadyEvent event) {
        repository.save(Wallet.emptyWallet(WalletId.fromString("2a727ad9-3243-483a-bf39-9840bbc0e459")));
    }
}

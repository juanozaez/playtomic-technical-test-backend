package com.playtomic.tests.wallet.acceptance.topup;

import com.playtomic.tests.AcceptanceTest;
import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.card.mother.CardMother;
import com.playtomic.tests.wallet.domain.Balance;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.fake.StripeMockServer;
import com.playtomic.tests.wallet.mother.WalletMother;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

public class TopUpWalletAcceptanceTest extends AcceptanceTest {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    protected void beforeEach() {
        stripeMockServer.start();
    }

    @Override
    protected void afterEach() {
        stripeMockServer.stop();
    }

    private final StripeMockServer stripeMockServer = new StripeMockServer();

    @Test
    public void tops_up_wallet() {
        walletExists();
        stripeMockServer.stubCharge(card, topUpAmount, "123456789");

        given().
                contentType("application/json").
                body(body).
                when().
                post("/wallets/{id}/transactions", wallet.getId().getValue()).
                then().
                statusCode(200);

        assert walletRepository.findById(wallet.getId()).balance().equals(expectedBalance);
    }

    private void walletExists() {
        walletRepository.save(wallet);
    }

    private final Wallet wallet = WalletMother.positiveWallet();
    private final BigDecimal topUpAmount = new BigDecimal("30.50");
    private final Balance expectedBalance = new Balance(topUpAmount.add(wallet.getBalance().amount));
    private final Card card = CardMother.valid();
    private final String body = String.format("""
            {
                "transactionId": "6ff22ba2-7c3a-43df-9bd2-7f35e40c1d9c",
                "amount": %.2f,
                "creditCard": {
                    "cardNumber": "%s"
                }
            }
            """, topUpAmount, card.number());
}
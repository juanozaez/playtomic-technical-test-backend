package com.playtomic.tests.wallet.acceptance.find;

import com.playtomic.tests.AcceptanceTest;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.mother.WalletMother;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

public class FindWalletAcceptanceTest extends AcceptanceTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void finds_wallet() throws JSONException {
        walletExists();

        String actualBody =
                given()
                        .contentType("application/json")
                .when()
                        .get("/wallets/{id}", wallet.getId().getValue())
                .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);
    }

    private void walletExists() {
        walletRepository.save(wallet);
    }

    private final Wallet wallet = WalletMother.positiveWallet();
    private final String expectedBody = String.format("""
        {
            "id": "%s",
            "balance": %.2f
        }
        """, wallet.getId().getValue(), wallet.getBalance().amount);
}

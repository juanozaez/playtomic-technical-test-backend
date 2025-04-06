package com.playtomic.tests.wallet.acceptance.find;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class FindWalletAcceptanceTest {

    @LocalServerPort
    private Integer port = 0;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void findsWallet() throws JSONException {
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

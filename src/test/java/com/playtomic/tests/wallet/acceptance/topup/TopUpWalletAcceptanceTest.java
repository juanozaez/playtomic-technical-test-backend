package com.playtomic.tests.wallet.acceptance.topup;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.mother.WalletMother;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class TopUpWalletAcceptanceTest {

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
    public void topsUpWallet() {
        walletExists();

        given().
                contentType("application/json").
                body(body).
        when().
                post("/wallets/{id}/transactions", wallet.getId().getValue()).
        then().
                statusCode(200);

        // TODO assertion
    }

    private void walletExists() {
        walletRepository.save(wallet);
    }

    private final Wallet wallet = WalletMother.positiveWallet();
    private final String body = """
            {
                "transactionId": "6ff22ba2-7c3a-43df-9bd2-7f35e40c1d9c",
                "amount": 30.50,
                "creditCard": {
                    "cardNumber": "4111111111111111"
                }
            }
            """;
}
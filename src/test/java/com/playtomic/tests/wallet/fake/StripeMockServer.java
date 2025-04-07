package com.playtomic.tests.wallet.fake;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.playtomic.tests.card.domain.Card;
import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class StripeMockServer {

    private final WireMockServer wireMockServer = new WireMockServer(9999);
    private final WireMock wiremock = new WireMock("localhost", 9999);

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }

    public String url() {
        return "http://localhost:9999/";
    }

    public void stubCharge(Card card, BigDecimal amount, String paymentId) {
        wiremock.register(
                post("/charges")
                        .withRequestBody(equalToJson("{ \"amount\": " + amount + ", \"credit_card\": \"" + card.numberAsString() + "\" }"))
                        .willReturn(
                                aResponse().
                                        withBody("{ \"id\": \"" + paymentId + "\" }").
                                        withHeader("Content-Type", "application/json").
                                        withStatus(200)));
    }

    public void stubChargeToFailureDueToMinAmount(Card card, BigDecimal amount) {
        wiremock.register(
                post("/charges")
                        .withRequestBody(equalToJson("{ \"amount\": " + amount + ", \"credit_card\": \"" + card.numberAsString() + "\" }"))
                        .willReturn(
                                aResponse().
                                        withHeader("Content-Type", "application/json").
                                        withStatus(422)));
    }

    public void stubChargeToFailure(Integer errorCode) {
        wiremock.register(
                post("/charges")
                        .willReturn(
                                aResponse().
                                        withHeader("Content-Type", "application/json").
                                        withStatus(errorCode)));
    }

    public void verifyCreateCharge(Card card, BigDecimal amount) {
        wiremock.verifyThat(
                postRequestedFor(
                        urlEqualTo("/charges")
                ).withRequestBody(equalToJson("{ \"amount\": " + amount.intValue() + ", \"credit_card\": \"" + card.numberAsString() + "\" }"))
        );
    }
}

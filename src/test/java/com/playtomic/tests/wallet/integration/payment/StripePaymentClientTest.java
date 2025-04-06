package com.playtomic.tests.wallet.integration.payment;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.card.mother.CardMother;
import com.playtomic.tests.wallet.fake.StripeMockServer;
import com.playtomic.tests.wallet.infrastructure.payment.StripePaymentClient;
import com.playtomic.tests.wallet.infrastructure.payment.error.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.infrastructure.payment.error.StripeServiceException;
import java.math.BigDecimal;
import java.net.URI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StripePaymentClientTest {

    private final StripeMockServer stripeMockServer = new StripeMockServer();
    private final URI chargesUrl = URI.create(stripeMockServer.url() + "charges");
    private final URI refundUrl = URI.create(stripeMockServer.url() + "refunds");
    private final StripePaymentClient client = new StripePaymentClient(chargesUrl, refundUrl, new RestTemplateBuilder());

    @BeforeEach
    void setUp() {
        stripeMockServer.start();
    }

    @AfterEach
    void tearDown() {
        stripeMockServer.stop();
    }

    @Test
    void charges_card_with_amount() {
        BigDecimal amount = BigDecimal.valueOf(10);
        String paymentId = "123456789";
        stripeMockServer.stubCharge(card, amount, paymentId);

        String result = client.charge(card, amount);

        assert result.equals(paymentId);
        stripeMockServer.verifyCreateCharge(card, amount);
    }

    @Test
    void returns_error_if_amount_is_less_than_10() {
        BigDecimal amount = BigDecimal.valueOf(9);
        stripeMockServer.stubChargeToFailureDueToMinAmount(card, amount);

        assertThrows(StripeAmountTooSmallException.class, () -> client.charge(card, amount));
    }

    @Test
    void returns_error_if_error_4xx() {
        BigDecimal amount = BigDecimal.valueOf(9);
        stripeMockServer.stubChargeToFailure(405);

        assertThrows(StripeServiceException.class, () -> client.charge(card, amount));
    }

    @Test
    void returns_error_if_error_5xx() {
        BigDecimal amount = BigDecimal.valueOf(9);
        stripeMockServer.stubChargeToFailure(503);

        assertThrows(StripeServiceException.class, () -> client.charge(card, amount));
    }

    private final Card card = CardMother.valid();


}

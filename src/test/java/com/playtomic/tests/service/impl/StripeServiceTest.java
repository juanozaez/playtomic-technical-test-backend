package com.playtomic.tests.service.impl;


import com.playtomic.tests.wallet.infrastructure.payment.error.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.infrastructure.payment.error.StripeServiceException;
import com.playtomic.tests.wallet.infrastructure.payment.StripePaymentClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.net.URI;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 */
public class StripeServiceTest {

    URI testUri = URI.create("http://how-would-you-test-me.localhost");
    StripePaymentClient s = new StripePaymentClient(testUri, testUri, new RestTemplateBuilder());

    @Test
    public void test_exception() {
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            //s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        //s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}

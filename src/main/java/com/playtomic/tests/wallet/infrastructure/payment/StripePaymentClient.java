package com.playtomic.tests.wallet.infrastructure.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.wallet.domain.PaymentClient;
import com.playtomic.tests.wallet.infrastructure.payment.error.StripeServiceException;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;


@Service
public class StripePaymentClient implements PaymentClient {

    @NonNull
    private URI chargesUri;

    @NonNull
    private URI refundsUri;

    @NonNull
    private RestTemplate restTemplate;

    public StripePaymentClient(@Value("${stripe.simulator.charges-uri}") @NonNull URI chargesUri,
                               @Value("${stripe.simulator.refunds-uri}") @NonNull URI refundsUri,
                               @NonNull RestTemplateBuilder restTemplateBuilder) {
        this.chargesUri = chargesUri;
        this.refundsUri = refundsUri;
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        this.restTemplate =
                restTemplateBuilder
                .errorHandler(new StripeRestTemplateResponseErrorHandler())
                        .messageConverters(converter)
                .build();
    }

    @Override
    public String charge(@NonNull Card card, @NonNull BigDecimal amount) throws StripeServiceException {
        ChargeRequest body = new ChargeRequest(card.number(), amount);
        return restTemplate.postForObject(chargesUri, body, Payment.class).getId();
    }

    @Override
    public void refund(@NonNull String paymentId) throws StripeServiceException {
        restTemplate.postForEntity(chargesUri.toString(), null, Object.class, paymentId);
    }

    @AllArgsConstructor
    private static class ChargeRequest {

        @NonNull
        @JsonProperty("credit_card")
        String creditCardNumber;

        @NonNull
        @JsonProperty("amount")
        BigDecimal amount;
    }
}

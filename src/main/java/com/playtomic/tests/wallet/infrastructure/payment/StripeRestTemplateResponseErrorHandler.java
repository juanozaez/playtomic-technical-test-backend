package com.playtomic.tests.wallet.infrastructure.payment;

import com.playtomic.tests.wallet.infrastructure.payment.error.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.infrastructure.payment.error.StripeServiceException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class StripeRestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            throw new StripeAmountTooSmallException();
        }
        throw new StripeServiceException();
    }
}

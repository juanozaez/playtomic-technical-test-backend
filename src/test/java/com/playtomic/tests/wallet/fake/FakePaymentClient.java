package com.playtomic.tests.wallet.fake;

import com.playtomic.tests.card.domain.Card;
import com.playtomic.tests.wallet.domain.PaymentClient;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.NonNull;

public class FakePaymentClient implements PaymentClient {

    private final Map<String, PaymentRecord> payments = new HashMap<>();

    public void reset() {
        payments.clear();
    }

    @Override
    public String charge(@NonNull Card card, @NonNull BigDecimal amount) {
        String paymentId = UUID.randomUUID().toString();
        payments.put(paymentId, new PaymentRecord(card.number(), amount, false));
        return paymentId;
    }

    @Override
    public void refund(@NonNull String paymentId) {
        PaymentRecord record = payments.get(paymentId);
        if (record == null) {
            throw new IllegalArgumentException("Payment ID not found: " + paymentId);
        }
        record.setRefunded(true);
    }

    public boolean isRefunded(String paymentId) {
        PaymentRecord record = payments.get(paymentId);
        if (record == null) {
            throw new IllegalArgumentException("Payment ID not found: " + paymentId);
        }
        return record.isRefunded();
    }

    public boolean chargeMade(BigDecimal amount) {
        return payments.values().stream()
                .anyMatch(payment -> payment.amount.compareTo(amount) == 0);
    }

    // Helper class to store payment information
    private static class PaymentRecord {
        private final String creditCardNumber;
        private final BigDecimal amount;
        private boolean refunded;

        public PaymentRecord(String creditCardNumber, BigDecimal amount, boolean refunded) {
            this.creditCardNumber = creditCardNumber;
            this.amount = amount;
            this.refunded = refunded;
        }

        public boolean isRefunded() {
            return refunded;
        }

        public void setRefunded(boolean refunded) {
            this.refunded = refunded;
        }
    }
}

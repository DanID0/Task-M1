package org.example.payment;

import org.example.model.PaymentResult;

import java.math.BigDecimal;

public abstract class PaymentMethod {
    private final String providerName;

    protected PaymentMethod(String providerName) {
        this.providerName = providerName;
    }

    public PaymentResult pay(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new PaymentResult(false, "Amount must be greater than zero.");
        }
        return processPayment(amount);
    }

    protected abstract PaymentResult processPayment(BigDecimal amount);

    public String getProviderName() {
        return providerName;
    }
}

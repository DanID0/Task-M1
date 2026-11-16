package org.example.payment;

import org.example.model.PaymentResult;

import java.math.BigDecimal;

public class PaypalPayment extends PaymentMethod {
    private final String email;

    public PaypalPayment(String email) {
        super("PayPal");
        this.email = email;
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (email == null || !email.contains("@")) {
            return new PaymentResult(false, "Incorrect email adress");
        }
        return new PaymentResult(true, "Paid " + amount + " using PayPal");
    }
}

package org.example.payment;

import org.example.model.PaymentResult;

import java.math.BigDecimal;

public class GiftCardPayment extends PaymentMethod {
    private final String code;
    private BigDecimal balance;

    public GiftCardPayment(String code, BigDecimal balance) {
        super("GiftCard");
        this.code = code;
        this.balance = balance;
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (code == null || code.isBlank()) {
            return new PaymentResult(false, "Invalid card code");
        }
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Gift card balance cannot be negative or null");
        }
        if (balance.compareTo(amount) < 0) {
            return new PaymentResult(false, "check your balance");
        }
        this.balance = balance.subtract(amount);
        return new PaymentResult(true, "Paid " + amount + " using gift card");

    }
}

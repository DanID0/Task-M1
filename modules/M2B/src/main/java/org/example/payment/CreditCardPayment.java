package org.example.payment;

import org.example.model.PaymentResult;

public class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        super("CreditCard");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (cardHolderName == null || cardHolderName.isBlank()) {
            return new PaymentResult(false, "cardholder name is invalid");
        }
        if (cardNumber == null || !cardNumber.matches("\\d{13,19}")) {
            return new PaymentResult(false, "Numbers of card are just wrong");
        }
        return new PaymentResult(true, "Paid " + amount + " using credit card ending with " + cardNumber.substring(cardNumber.length() - 4));
    }
}

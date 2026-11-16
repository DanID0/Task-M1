package org.example.payment;

import java.math.BigDecimal;

public class PaymentMethodFactory {

    public static PaymentMethod createCreditCardPayment(
            String cardNumber, String cardHolderName) {
        return new CreditCardPayment(cardNumber, cardHolderName);
    }

    public static PaymentMethod createPaypalPayment(String email) {
        return new PaypalPayment(email);
    }

    public static PaymentMethod createGiftCardPayment(String code, BigDecimal balance) {
        return new GiftCardPayment(code, balance);
    }

    public static PaymentMethod createBankTransfer(String iban, String accountHolderName) {
        return new BankTransfer(iban, accountHolderName);
    }
}


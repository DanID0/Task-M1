package org.example.payment;

import org.example.model.PaymentResult;

import java.math.BigDecimal;

public class BankTransfer extends PaymentMethod {
    private final String iban;
    private final String accountHolderName;

    public BankTransfer(String iban, String accountHolderName) {
        super("BankTransfer");
        this.iban = iban;
        this.accountHolderName = accountHolderName;
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (accountHolderName == null || accountHolderName.isBlank()) {
            return new PaymentResult(false, "Account holder name is invalid");
        }
        if (iban == null || iban.isBlank()) {
            return new PaymentResult(false, "Invalid IBAN");
        }
        return new PaymentResult(true, "Paid " + amount + " using bank transfer ");
    }

}

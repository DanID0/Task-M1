package org.example.model;

import java.math.BigDecimal;

public class FixedAmountDiscount extends Discount {
    private final BigDecimal amount;

    public FixedAmountDiscount(String code, BigDecimal amount) {
        super(code);
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public BigDecimal apply(BigDecimal originalAmount) {
        return originalAmount.subtract(amount).max(BigDecimal.ZERO);

    }
}

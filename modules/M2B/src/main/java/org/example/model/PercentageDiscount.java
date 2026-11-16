package org.example.model;

import java.math.BigDecimal;

public class PercentageDiscount extends Discount {
    private final BigDecimal percentage;

    public PercentageDiscount(String code, BigDecimal percentage) {
        super(code);
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage cannot be negative or over 100");
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal apply(BigDecimal originalAmount) {
        return originalAmount.subtract(originalAmount.multiply(percentage).divide(BigDecimal.valueOf(100)));
    }
}

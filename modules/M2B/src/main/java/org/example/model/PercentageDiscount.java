package org.example.model;

public class PercentageDiscount extends Discount {
    private final double percentage;

    public PercentageDiscount(String code, double percentage) {
        super(code);
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage cannot be negative or over 100");
        }
        this.percentage = percentage;
    }

    @Override
    public double apply(double originalAmount) {
        return originalAmount - (originalAmount * percentage / 100);
    }
}

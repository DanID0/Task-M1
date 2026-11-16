package org.example.model;

import java.math.BigDecimal;

public class NoDiscount extends Discount {
    public NoDiscount() {
        super("NONE");
    }

    @Override
    public BigDecimal apply(BigDecimal originalAmount) {
        return originalAmount;
    }
}

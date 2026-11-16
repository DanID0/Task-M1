package org.example.model;

import java.math.BigDecimal;

public abstract class Discount {
    private final String code;

    protected Discount(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public abstract BigDecimal apply(BigDecimal originalAmount);
}

package org.example.model;

import java.math.BigDecimal;


public class OrderItem {
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public OrderItem(String name, BigDecimal price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank.");
        } else if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public BigDecimal calculateTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " = " + calculateTotal();
    }
}

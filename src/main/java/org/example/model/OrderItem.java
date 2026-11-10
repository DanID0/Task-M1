package org.example.model;

public class OrderItem {
    private final String name;
    private final double price;
    private final int quantity;

    public OrderItem(String name, double price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank.");
        } else if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public double calculateTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " = " + calculateTotal();
    }
}

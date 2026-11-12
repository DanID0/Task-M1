package org.example.model;

import lombok.Getter;
import org.example.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Discount discount = new NoDiscount();

    public Order(Builder builder) {
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item) {
        if (status == OrderStatus.PAID) {
            throw new IllegalStateException("Order has been paid, so this is not allowed");
        }
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.calculateTotal();
        }
        total = discount.apply(total);
        total *= (1 + AppConfig.getInstance().getTaxRate());
        return total;
    }

    public void markAsPaid() {
        if (items.isEmpty()) {
            throw new IllegalStateException("There is nothing to pay for");
        }
        this.status = OrderStatus.PAID;
    }

    public void applyDiscount(Discount discount) {
        if (isPaid()) {
            throw new IllegalStateException("Cannot change discount on a paid order");
        }
        if (discount == null) {
            throw new IllegalStateException("Discount cant be null");
        }

        this.discount = discount;
    }

    public boolean isPaid() {
        return this.status == OrderStatus.PAID;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerName;
        private List<OrderItem> items = new ArrayList<>();

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder addItem(OrderItem item) {
            this.items.add(item);
            return this;
        }

        public Order build() {
            if (customerName == null || customerName.isBlank()) {
                throw new IllegalArgumentException("Customer name cannot be null or blank.");
            }
            return new Order(this);
        }

    }

}

package org.example.payment;

import org.example.model.Order;
import org.example.model.PaymentResult;

import java.util.ArrayList;
import java.util.List;

public class PaymentProcessor {
    private final List<Order> completedOrders = new ArrayList<>();

    public List<Order> getCompletedOrders() {
        return List.copyOf(completedOrders);
    }

    public PaymentResult process(Order order, PaymentMethod paymentMethod) {

        if (paymentMethod == null) {
            return new PaymentResult(false, "Payment method is missing");
        }
        if (order == null) {
            return new PaymentResult(false, "Order does not exist");
        }
        if (order.isPaid()) {
            return new PaymentResult(false, "Order is already paid for");
        }
        if (order.getItems().isEmpty()) {
            return new PaymentResult(false, "Order is empty");
        }


        PaymentResult result = paymentMethod.pay(order.calculateTotal());

        if (result.isSuccessful()) {
            order.markAsPaid();
            completedOrders.add(order);
        }

        return result;
    }
}

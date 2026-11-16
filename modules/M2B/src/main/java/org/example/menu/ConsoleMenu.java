package org.example.menu;

import org.example.config.AppConfig;
import org.example.model.*;
import org.example.payment.PaymentMethod;
import org.example.payment.PaymentMethodFactory;
import org.example.payment.PaymentProcessor;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private Order currentOrder;

    public void start() {
        AppConfig config = AppConfig.getInstance();
        System.out.println("Welcome to " + config.getApplicationName());
        boolean running = true;
        while (running) {
            printMenu();
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (option) {
                case 1 -> createOrder();
                case 2 -> addItem();
                case 3 -> viewOrder();
                case 4 -> displayOrders();
                case 5 -> payOrder();
                case 6 -> applyDiscount();
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createOrder() {
        System.out.println("Customer name:");
        String customerName = scanner.nextLine();

        try {
            currentOrder = Order.builder()
                    .customerName(customerName)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid customer name: " + e.getMessage());
            return;
        }

        System.out.println("Order created for " + customerName);
    }

    private boolean orderExists() {
        if (currentOrder == null) {
            System.out.println("Please create an order first.");
            return false;
        }
        return true;
    }

    private void addItem() {
        if (!orderExists()) {
            return;
        }
        if (currentOrder.isPaid()) {
            System.out.println("Cannot add items to a paid order.");
            return;
        }
        System.out.println("Item name:");
        String itemName = scanner.nextLine();

        BigDecimal price;
        int quantity;

        try {
            System.out.println("Price:");
            price = new BigDecimal(scanner.nextLine());

            System.out.println("Quantity:");
            quantity = Integer.parseInt(scanner.nextLine());

            currentOrder.addItem(new OrderItem(itemName, price, quantity));

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid item: " + e.getMessage());
            return;
        }
        System.out.println("Item added to order");
    }

    private void viewOrder() {
        if (!orderExists()) {
            return;
        }
        String currency = AppConfig.getInstance().getCurrency();
        System.out.println("Customer: " + currentOrder.getCustomerName());
        System.out.println("Status: " + currentOrder.getStatus());
        System.out.println("Items:");

        for (OrderItem item : currentOrder.getItems()) {
            System.out.println("- " + item);
        }

        System.out.println("Total: " + currentOrder.calculateTotal() + " " + currency);
    }

    private void displayOrders() {
        for (Order order : paymentProcessor.getCompletedOrders()) {
            System.out.println("Customer: " + order.getCustomerName());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total: " + order.calculateTotal());
        }

    }

    private void payOrder() {
        if (!orderExists()) {
            return;
        }


        System.out.println("""
                Select payment method:
                1. Credit Card
                2. PayPal
                3. Gift Card
                4. Bank Transfer
                """);
        int option;
        PaymentMethod paymentMethod;
        try {
            option = Integer.parseInt(scanner.nextLine());
            paymentMethod = switch (option) {
                case 1 -> createCreditCardPayment();
                case 2 -> createPaypalPayment();
                case 3 -> createGiftCardPayment();
                case 4 -> createBankTransfer();
                default -> throw new IllegalArgumentException("Invalid payment method");
            };

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid payment method.");
            return;
        }

        try {
            PaymentResult result = paymentProcessor.process(currentOrder, paymentMethod);
            System.out.println(result.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

    private PaymentMethod createCreditCardPayment() {
        System.out.println("Card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Card holder name:");
        String cardHolderName = scanner.nextLine();

        return PaymentMethodFactory.createCreditCardPayment(cardNumber, cardHolderName);
    }

    private PaymentMethod createPaypalPayment() {
        System.out.println("PayPal email:");
        String email = scanner.nextLine();

        return PaymentMethodFactory.createPaypalPayment(email);
    }

    private PaymentMethod createGiftCardPayment() {
        System.out.println("Gift card code:");
        String code = scanner.nextLine();

        System.out.println("Gift card balance:");
        BigDecimal balance = new BigDecimal(scanner.nextLine());

        return PaymentMethodFactory.createGiftCardPayment(code, balance);
    }

    private PaymentMethod createBankTransfer() {
        System.out.println("Your bank Iban:");
        String iban = scanner.nextLine();
        System.out.println("Account holder name:");
        String accountHolderName = scanner.nextLine();
        return PaymentMethodFactory.createBankTransfer(iban, accountHolderName);
    }

    private void applyDiscount() {
        if (!orderExists()) {
            return;
        }
        if (currentOrder.isPaid()) {
            System.out.println("Cannot apply a discount to a paid order.");
            return;
        }
        System.out.println("""
                Select discount option:
                1. No discount
                2. Fixed amount discount
                3. Percentage discount
                """);

        try {
            int answer;
            answer = Integer.parseInt(scanner.nextLine());
            switch (answer) {
                case 1 -> currentOrder.applyDiscount(new NoDiscount());
                case 2 -> {
                    System.out.println(" Enter discount amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    currentOrder.applyDiscount(new FixedAmountDiscount("FIXED", amount));
                }
                case 3 -> {
                    System.out.println(" Enter discount amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    currentOrder.applyDiscount(new PercentageDiscount("Percentage", amount));
                }
                default -> throw new IllegalArgumentException("Invalid option");

            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    private void printMenu() {
        System.out.println("""
                1. Create order
                2. Add item to order
                3. View order
                4. View completed orders
                5. Pay order
                6. Apply discount
                0. Exit
                """);
    }
}

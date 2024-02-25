package org.example.dashboards.manager;

import org.example.dashboards.Page;
import org.example.entities.Order;
import org.example.services.OrderService;
import org.example.services.impl.OrderServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManagerViewAllOrdersPage implements Page {
    private final OrderService orderService;

    public ManagerViewAllOrdersPage() {
        this.orderService = OrderServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View all orders";
    }

    @Override
    public void start() {
        printAsTable(orderService.getAll());
    }

    public void printAsTable(List<Order> orders) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String horizontalLine = "+------+----------------------+----------------+------------------+--------------+-------------------------+-----------------+\n";
        System.out.print(horizontalLine);
        System.out.printf("| %-4s | %-20s | %-14s | %-16s | %-12s | %-23s | %-15s |\n", "ID", "Order Note", "Address", "Payment Method", "Order Type", "Order Date", "Pizza Orders");
        System.out.print(horizontalLine);
        for (var order : orders) {
            System.out.printf("| %-4d | %-20s | %-14s | %-16s | %-12s | %-23s | %-15d |\n",
                    order.getId(), order.getOrderNote(), order.getAddress(),
                    order.getPaymentMethod().name(), order.getOrderType().name(), order.getOrderDate().format(dateTimeFormatter), order.getPizzaOrders().size());
            System.out.print(horizontalLine);
        }
    }
}

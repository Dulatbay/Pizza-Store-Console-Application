package org.example.dashboards.manager;

import org.example.dashboards.Page;
import org.example.dashboards.general.ViewAllIngredientsPage;
import org.example.entities.PizzaOrder;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.OrderService;
import org.example.services.impl.OrderServiceImpl;
import org.example.utils.InputUtils;

import java.util.List;

public class ManagerViewOrderDetailPage implements Page {

    private final OrderService orderService;

    public ManagerViewOrderDetailPage() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View order's pizzas";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        try {
            System.out.print("Enter a id of order to detail view:");
            var line = InputUtils.getScanner().nextLine();
            if (line.equals("exit")) return;
            var id = Long.parseLong(line);
            var entity = orderService.getById(id);
            printOrdersAsTable(entity.getPizzaOrders());
        } catch (NumberFormatException e) {
            System.out.println("Enter a number!");
        } catch (EntityNotFoundException e) {
            System.out.println("Order doesn't exist");
        }
    }

    public void printOrdersAsTable(List<PizzaOrder> orders) {
        String horizontalLine = "+------+-----------------+-------------+--------------+----------------+-----------------+\n";
        System.out.print(horizontalLine);
        System.out.printf("| %-4s | %-15s | %-11s | %-12s | %-14s | %-15s |\n", "ID", "Pizza", "Total Price", "Size", "Dough", "Note");
        System.out.print(horizontalLine);
        for (PizzaOrder order : orders) {
            System.out.printf("| %-4d | %-15s | %-11.2f | %-12s | %-14s |  %-15s |\n",
                    order.getId(), order.getPizzaBase().getName(), order.getTotalPrice(),
                    order.getSelectedPizzaSize().getDescription(), order.getSelectedPizzaDough().getDescription(),
                    order.getNote());
            System.out.print(horizontalLine);
            System.out.println("Added ingredients: ");
            new ViewAllIngredientsPage().printAsTable(order.getAddedIngredients());
        }
    }

}

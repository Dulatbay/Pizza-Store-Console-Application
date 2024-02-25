package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaSize;
import org.example.services.PizzaSizeService;
import org.example.services.impl.PizzaSizeServiceImpl;

import java.util.List;

public class ViewAllPizzaSizesPage implements Page {
    private final PizzaSizeService pizzaSizeService;

    public ViewAllPizzaSizesPage() {
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View all pizza sizes";
    }

    @Override
    public void start() {
        printAsTable(pizzaSizeService.getAll());
    }

    public void printAsTable(List<PizzaSize> pizzaSizes) {
        String horizontalLine = "+------+------------------+------------+------------+\n";
        System.out.print(horizontalLine);
        System.out.printf("| %-4s | %-16s | %-10s | %-10s |\n", "ID", "Description", "Centimeter", "Price");
        System.out.print(horizontalLine);
        for (var i : pizzaSizes) {
            System.out.printf("| %-4d | %-16s | %-10d | %-10s |\n", i.getId(), i.getDescription(), i.getCentimeter(), i.getPrice());
            System.out.print(horizontalLine);
        }
    }
}

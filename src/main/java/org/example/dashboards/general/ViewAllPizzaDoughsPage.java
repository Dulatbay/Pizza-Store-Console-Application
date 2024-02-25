package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaDough;
import org.example.services.PizzaDoughService;
import org.example.services.impl.PizzaDoughServiceImpl;

import java.util.List;

public class ViewAllPizzaDoughsPage implements Page {

    private final PizzaDoughService pizzaDoughService;

    public ViewAllPizzaDoughsPage(){
        pizzaDoughService = PizzaDoughServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View all pizza dough";
    }

    @Override
    public void start() {
        printAsTable(this.pizzaDoughService.getAll());
    }

    public void printAsTable(List<PizzaDough> pizzaDoughList) {
        String horizontalLine = "+------+------------------+---------+\n";
        System.out.print(horizontalLine);
        System.out.printf("| %-4s | %-16s | %-7s |\n", "ID", "Description", "Price");
        System.out.print(horizontalLine);
        for (PizzaDough pizzaDough : pizzaDoughList) {
            System.out.printf("| %-4d | %-16s | %-7.2f |\n", pizzaDough.getId(), pizzaDough.getDescription(), pizzaDough.getPrice());
            System.out.print(horizontalLine);
        }
    }
}

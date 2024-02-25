package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaBase;
import org.example.services.PizzaBaseService;
import org.example.services.impl.PizzaBaseServiceImpl;

import java.util.List;

public class ViewAllPizzasPage implements Page {

    private final PizzaBaseService pizzaBaseService;

    public ViewAllPizzasPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View all pizzas";
    }

    @Override
    public void start() {
        printAsTable(pizzaBaseService.getAll());
    }

    public void printAsTable(List<PizzaBase> pizzaBases) {
        final String horizontalLine = "+------+-----------------+-------------+-----------+-----------+--------------------+--------------------+\n";
        final String format = "| %-4s | %-15s | %-11s | %-9s | %-9s | %-18s | %-18s |\n";
        System.out.print(horizontalLine);
        System.out.printf(format, "ID", "Name", "Base Price", "Doughs", "Sizes", "Base Ingredients", "Extra Ingredients");
        System.out.print(horizontalLine);
        for (var i : pizzaBases) {
            System.out.printf(format,
                    i.getId(), i.getName(), i.getBasePrice(), i.getPizzaDoughs().size(), i.getPizzaSizes().size(), i.getBaseIngredients().size(), i.getExtraIngredients().size());
            System.out.print(horizontalLine);
        }
    }
}

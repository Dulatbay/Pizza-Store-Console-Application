package org.example.dashboards.general;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaIngredient;
import org.example.services.PizzaIngredientService;
import org.example.services.impl.PizzaIngredientServiceImpl;

import java.util.List;

public class ViewAllIngredientsPage implements Page {

    private final PizzaIngredientService pizzaIngredientService;

    public ViewAllIngredientsPage() {
        pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "View all ingredients";
    }

    @Override
    public void start() {
        this.printAsTable(pizzaIngredientService.getAll());
    }

    public void printAsTable(List<PizzaIngredient> pizzaIngredients) {
        String horizontalLine = "+------+------------------+---------+\n";
        System.out.print(horizontalLine);
        System.out.printf("| %-4s | %-16s | %-7s |\n", "ID", "Description", "Price");
        System.out.print(horizontalLine);
        for (PizzaIngredient pizzaIngredient : pizzaIngredients) {
            System.out.printf("| %-4d | %-16s | %-7.2f |\n", pizzaIngredient.getId(), pizzaIngredient.getDescription(), pizzaIngredient.getPrice());
            System.out.print(horizontalLine);
        }
    }
}

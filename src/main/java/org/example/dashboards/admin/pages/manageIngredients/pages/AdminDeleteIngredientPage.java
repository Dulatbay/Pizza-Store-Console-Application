package org.example.dashboards.admin.pages.manageIngredients.pages;

import org.example.dashboards.Page;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.PizzaIngredientService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.services.impl.PizzaIngredientServiceImpl;
import org.example.utils.InputUtils;

public class AdminDeleteIngredientPage implements Page {

    private final PizzaIngredientService pizzaIngredientService;
    private final PizzaBaseService pizzaBaseService;

    public AdminDeleteIngredientPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Delete ingredient";
    }

    @Override
    public void start() {
        System.out.print("Enter a id of pizza size: ");
        var line = InputUtils.getScanner().nextLine();
        try {
            var id = Long.parseLong(line);

            var entity = pizzaIngredientService.getById(id);
            var used = pizzaBaseService.getAll().stream().filter(i -> i.getBaseIngredients().contains(entity)).findAny();

            if (used.isPresent()) {
                System.out.println("The ingredients has already been used as base ingredient. Remove the pizza before removing the ingredient.");
                return;
            }

            pizzaIngredientService.deleteById(id);
            System.out.println("Deleted");
        } catch (EntityNotFoundException entityNotFoundException) {
            System.out.println("Entity doesn't exist");
        } catch (Exception e) {
            System.out.println("Some error occurred: " + e.getMessage());
        }
    }
}

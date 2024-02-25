package org.example.dashboards.admin.pages.managePizzaDoughs.pages;

import org.example.dashboards.Page;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.PizzaDoughService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.services.impl.PizzaDoughServiceImpl;
import org.example.utils.InputUtils;

public class AdminDeletePizzaDoughPage implements Page {
    private final PizzaDoughService pizzaDoughService;
    private final PizzaBaseService pizzaBaseService;

    public AdminDeletePizzaDoughPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        pizzaDoughService = PizzaDoughServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Delete pizza dough";
    }

    @Override
    public void start() {
        System.out.print("Enter a id of pizza size: ");
        var line = InputUtils.getScanner().nextLine();
        try {
            var id = Long.parseLong(line);
            var entity = pizzaDoughService.getById(id);
            var used = pizzaBaseService.getAll().stream().filter(i -> i.getPizzaDoughs().contains(entity)).findAny();

            if (used.isPresent()) {
                System.out.println("The dough has already been used. Remove the pizza before removing the dough.");
                return;
            }

            pizzaDoughService.deleteById(id);
            System.out.println("Deleted");
        } catch (EntityNotFoundException entityNotFoundException) {
            System.out.println("Entity doesn't exist");
        } catch (Exception e) {
            System.out.println("Some error occurred: " + e.getMessage());
        }
    }
}

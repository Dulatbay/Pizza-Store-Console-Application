package org.example.dashboards.admin.pages.managePizzaSizes.pages;

import org.example.dashboards.Page;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.PizzaSizeService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.services.impl.PizzaSizeServiceImpl;
import org.example.utils.InputUtils;

public class AdminDeletePizzaSizePage implements Page {

    private final PizzaSizeService pizzaSizeService;
    private final PizzaBaseService pizzaBaseService;

    public AdminDeletePizzaSizePage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Delete pizza size";
    }

    @Override
    public void start() {
        System.out.print("Enter a id of pizza size: ");
        var line = InputUtils.getScanner().nextLine();
        try {
            var id = Long.parseLong(line);
            var entity = pizzaSizeService.getById(id);
            var used = pizzaBaseService.getAll().stream().filter(i -> i.getPizzaSizes().contains(entity)).findAny();
            if(used.isPresent()){
                System.out.println("The size has already been used. Remove the pizza before removing the size.");
                return;
            }

            pizzaSizeService.deleteById(id);
            System.out.println("Deleted");
        } catch (EntityNotFoundException entityNotFoundException) {
            System.out.println("Entity doesn't exist");
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number!!");
        }
    }
}

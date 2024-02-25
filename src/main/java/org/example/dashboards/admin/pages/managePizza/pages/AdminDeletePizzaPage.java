package org.example.dashboards.admin.pages.managePizza.pages;

import org.example.dashboards.Page;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.utils.InputUtils;

public class AdminDeletePizzaPage implements Page {
    private final PizzaBaseService pizzaBaseService;

    public AdminDeletePizzaPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();

    }


    @Override
    public String getName() {
        return "Delete pizza";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        try {
            System.out.print("Enter id of pizza base: ");
            var line = InputUtils.getScanner().nextLine();
            var id = Long.parseLong(line);
            pizzaBaseService.deleteById(id);
            System.out.println("Deleted");
        } catch (NumberFormatException e) {
            System.out.println("Enter a correct value!");
        } catch (EntityNotFoundException e) {
            System.out.println("Pizza base doesn't exist!");
        }
    }
}

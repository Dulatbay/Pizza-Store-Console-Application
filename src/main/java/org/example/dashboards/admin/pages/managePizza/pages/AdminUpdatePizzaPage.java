package org.example.dashboards.admin.pages.managePizza.pages;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaBase;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.utils.InputUtils;

public class AdminUpdatePizzaPage implements Page {

    private boolean exit = false;
    private final PizzaBaseService pizzaBaseService;

    public AdminUpdatePizzaPage() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Update pizza";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        System.out.print("Enter a id of pizza base of update: ");
        try {
            var line = InputUtils.getScanner().nextLine();
            long id = Long.parseLong(line);
            var pizza = pizzaBaseService.getById(id);
            setPizzaName(pizza);
            setBasePrice(pizza);
            pizzaBaseService.update(id, pizza);
            System.out.println("Updated");
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid value!");
        } catch (EntityNotFoundException e) {
            System.out.println("Entity doesn't exist");
        }
    }


    private void setPizzaName(PizzaBase pizzaBase) {
        while (!exit) {
            System.out.print("Enter a name of pizza: ");
            var line = InputUtils.getScanner().nextLine();

            if (line.equals("exit")) {
                exit = true;
                break;
            }

            if (line.contains("[") || line.contains("]")) {
                System.out.println("Write without [] symbols!!!");
                continue;
            }
            if (line.isBlank()) {
                System.out.println("Write a correct value!");
                continue;
            }
            if (pizzaBaseService.getByName(line).isPresent()) {
                System.out.println("Size with that description already added!");
                continue;
            }
            pizzaBase.setName(line);
            break;
        }
    }

    private void setBasePrice(PizzaBase pizzaBase) {
        while (!exit) {
            System.out.print("Enter a base price: ");
            var line = InputUtils.getScanner().nextLine();

            if (line.equals("exit")) {
                exit = true;
                break;
            }

            if (line.contains("[") || line.contains("]")) {
                System.out.println("Write without [] symbols!!!");
                continue;
            }
            if (line.isBlank()) {
                System.out.println("Write a correct value!");
                continue;
            }
            try {
                var number = Double.parseDouble(line);

                if (number < 10) {
                    System.out.println("Write a correct value! Min - 10");
                    continue;
                }

                pizzaBase.setBasePrice(number);
            } catch (Exception e) {
                System.out.println("Write a correct value!");
                continue;
            }
            break;
        }
    }
}

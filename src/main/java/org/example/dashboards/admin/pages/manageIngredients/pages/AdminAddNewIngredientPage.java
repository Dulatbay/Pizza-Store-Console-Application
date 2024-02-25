package org.example.dashboards.admin.pages.manageIngredients.pages;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaIngredient;
import org.example.services.PizzaIngredientService;
import org.example.services.impl.PizzaIngredientServiceImpl;
import org.example.utils.InputUtils;

public class AdminAddNewIngredientPage implements Page {

    private final PizzaIngredientService pizzaIngredientService;

    public AdminAddNewIngredientPage() {
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
    }


    @Override
    public String getName() {
        return "Add new ingredient";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        PizzaIngredient pizzaIngredient = new PizzaIngredient();
        boolean exit = false;
        while (true) {
            System.out.print("Enter a description of ingredient(tomato, cheese...): ");
            var line = InputUtils.getScanner().nextLine().trim();

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
            if (pizzaIngredientService.getByDescription(line).isPresent()) {
                System.out.println("Size with that description already added!");
                continue;
            }
            pizzaIngredient.setDescription(line);
            break;
        }

        if (exit)
            return;


        while (true) {
            System.out.print("Enter a price: ");
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

                pizzaIngredient.setPrice(number);
            } catch (Exception e) {
                System.out.println("Write a correct value!");
                continue;
            }
            break;
        }

        if (exit)
            return;


        try {
            pizzaIngredientService.create(pizzaIngredient);
            System.out.println("Created");
        } catch (Exception e) {
            System.out.println("Cannot create object");
        }
    }
}

package org.example.dashboards.admin.pages.managePizzaSizes.pages;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaSize;
import org.example.services.PizzaSizeService;
import org.example.services.impl.PizzaSizeServiceImpl;
import org.example.utils.InputUtils;

public class AdminAddNewPizzaSizePage implements Page {

    private final PizzaSizeService pizzaSizeService;

    public AdminAddNewPizzaSizePage() {
        pizzaSizeService = PizzaSizeServiceImpl.getInstance();
    }


    @Override
    public String getName() {
        return "Add new pizza size";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        PizzaSize pizzaSize = new PizzaSize();
        boolean exit = false;
        while (true) {
            System.out.print("Enter a description of size(small, medium, large...): ");
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
            if (pizzaSizeService.getByDescription(line).isPresent()) {
                System.out.println("Size with that description already added!");
                continue;
            }
            pizzaSize.setDescription(line);
            break;
        }

        if (exit)
            return;


        while (true) {
            System.out.print("Enter a centimeter of size: ");
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
                var number = Integer.parseInt(line);

                if (number < 10 || number > 100) {
                    System.out.println("Write a correct value! Min - 10, Max - 100");
                    continue;
                }

                pizzaSize.setCentimeter(number);
            } catch (Exception e) {
                System.out.println("Write a correct value!");
                continue;
            }
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

                pizzaSize.setPrice(number);
            } catch (Exception e) {
                System.out.println("Write a correct value!");
                continue;
            }
            break;
        }

        try {
            pizzaSizeService.create(pizzaSize);
            System.out.println("Created");
        } catch (Exception e) {
            System.out.println("Cannot create object");
        }
    }
}

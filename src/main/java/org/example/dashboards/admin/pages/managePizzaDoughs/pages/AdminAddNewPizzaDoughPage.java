package org.example.dashboards.admin.pages.managePizzaDoughs.pages;

import org.example.dashboards.Page;
import org.example.entities.pizza.PizzaDough;
import org.example.entities.pizza.PizzaSize;
import org.example.services.PizzaDoughService;
import org.example.services.impl.PizzaDoughServiceImpl;
import org.example.utils.InputUtils;

public class AdminAddNewPizzaDoughPage implements Page {

    private final PizzaDoughService pizzaDoughService;

    public AdminAddNewPizzaDoughPage() {
        this.pizzaDoughService = PizzaDoughServiceImpl.getInstance();
    }


    @Override
    public String getName() {
        return "Add new pizza dough";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        PizzaDough pizzaDough = new PizzaDough();
        boolean exit = false;
        while (true) {
            System.out.print("Enter a name of dough(traditional, thin...): ");
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
            if (pizzaDoughService.getByDescription(line).isPresent()) {
                System.out.println("Size with that description already added!");
                continue;
            }
            pizzaDough.setDescription(line);
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

                if(number < 10){
                    System.out.println("Write a correct value! Min - 10");
                    continue;
                }

                pizzaDough.setPrice(number);
            } catch (Exception e) {
                System.out.println("Write a correct value!");
                continue;
            }
            break;
        }

        if (exit)
            return;


        try {
            pizzaDoughService.create(pizzaDough);
            System.out.println("Created");
        } catch (Exception e) {
            System.out.println("Cannot create object");
        }
    }
}

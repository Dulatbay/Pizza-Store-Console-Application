package org.example;

import org.example.dashboards.Application;
import org.example.dashboards.admin.AdminApplication;
import org.example.dashboards.manager.ManagerApplication;
import org.example.services.*;
import org.example.services.impl.*;
import org.example.utils.InputUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.constants.ValueConstants.DB_DIR;


// https://t.me/channel_qanly
public class PizzaDashboard {

    private static void init() {
        PizzaDashboard.createDir();
        PizzaSizeService pizzaSizeService = PizzaSizeServiceImpl.getInstance();
        PizzaDoughService pizzaDoughService = PizzaDoughServiceImpl.getInstance();
        PizzaIngredientService pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
        PizzaBaseService pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        PizzaOrderService pizzaOrderService = PizzaOrderServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();

        try {
            pizzaSizeService.init();
            pizzaDoughService.init();
            pizzaIngredientService.init();
            pizzaBaseService.init();
            pizzaOrderService.init();
            orderService.init();
        } catch (FileNotFoundException ignored) {
        }
    }

    private static String getRoleSelectorMenu() {
        return "1) Enter as admin\n2) Enter as manager\n3) Exit";
    }

    public static void main(String[] args) {
        init();
        while (true) {
            System.out.println(getRoleSelectorMenu());
            System.out.print("Enter a number: ");
            var scLine = InputUtils.getScanner().nextLine();
            try {
                var num = Integer.parseInt(scLine);
                if (num > 3 || num < 1) {
                    System.out.println("Enter a valid number!");
                } else {
                    if (num == 3)
                        return;
                    getRole(num).start();
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static Application getRole(int number) {
        return switch (number) {
            case 1 -> new AdminApplication();
            case 2 -> new ManagerApplication();
            default -> throw new IllegalArgumentException();
        };
    }


    private static void createDir() {
        Path dirPath = Paths.get(DB_DIR);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
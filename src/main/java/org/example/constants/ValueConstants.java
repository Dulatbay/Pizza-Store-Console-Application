package org.example.constants;

import org.example.utils.InputUtils;

public class ValueConstants {
    private ValueConstants() {
    }


    public static final String DB_DIR = "upload-dir";
    public static final String PIZZA_BASE_FILE = "/pizza-base.txt";
    public static final String PIZZA_DOUGH_FILE = "/pizza-dough.txt";
    public static final String PIZZA_SIZE_FILE = "/pizza-size.txt";
    public static final String PIZZA_INGREDIENT_FILE = "/pizza-ingredient.txt";
    public static final String PIZZA_BASE_EXTRA_INGREDIENTS_FILE = "/pizza-base-extra-ingredients.txt";
    public static final String PIZZA_BASE_INGREDIENTS_FILE = "/pizza-base-ingredients.txt";
    public static final String PIZZA_BASE_SIZE_FILE = "/pizza-base-size.txt";
    public static final String PIZZA_BASE_DOUGH_FILE = "/pizza-base-dough.txt";
    public static final String PIZZA_ORDER_FILE = "/pizza-order.txt";
    public static final String PIZZA_ORDER_ADDED_INGREDIENTS_FILE = "/pizza-order-added-ingredients.txt";
    public static final String ORDER_FILE = "/order.txt";
    public static final String ORDER_PIZZA_ORDER_FILE = "/order-pizza-order.txt";

    public static int getValidNumber(int max) {
        do {
            System.out.print("Enter a number: ");
            var line = InputUtils.getScanner().nextLine();
            try {
                var number = Integer.parseInt(line);
                if (number < 1 || number > max) {
                    System.out.println("Enter valid a number!");
                    continue;
                }
                return number;
            } catch (Exception e) {
                System.out.println("Enter a number!");
            }
        } while (true);
    }
}

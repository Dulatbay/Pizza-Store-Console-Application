package org.example.dashboards.admin.pages.managePizza.pages;

import org.example.dashboards.Page;
import org.example.dashboards.general.ViewAllIngredientsPage;
import org.example.dashboards.general.ViewAllPizzaDoughsPage;
import org.example.dashboards.general.ViewAllPizzaSizesPage;
import org.example.entities.pizza.PizzaBase;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.PizzaDoughService;
import org.example.services.PizzaIngredientService;
import org.example.services.PizzaSizeService;
import org.example.services.impl.PizzaBaseServiceImpl;
import org.example.services.impl.PizzaDoughServiceImpl;
import org.example.services.impl.PizzaIngredientServiceImpl;
import org.example.services.impl.PizzaSizeServiceImpl;
import org.example.utils.InputUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AdminAddNewPizzaPage implements Page {

    private final PizzaBaseService pizzaBaseService;
    private final PizzaIngredientService pizzaIngredientService;
    private final PizzaSizeService pizzaSizeService;
    private final PizzaDoughService pizzaDoughService;
    boolean exit = false;


    public AdminAddNewPizzaPage() {
        this.pizzaDoughService = PizzaDoughServiceImpl.getInstance();
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Add new pizza";
    }


    // 1) name
    // 2) base pizza ingredients
    // 3) available pizza dough
    // 4) available pizza size
    // 5) extra ingredients
    // 6) base price


    // 2.1 ) Show All Ingredients
    // 2.2 ) Add ingredients by id;
    @Override
    public void start() {
        if (!isAvailable()) {
            System.out.println("Please fill in all the necessary information to create a pizza");
            return;
        }
        System.out.println("Write \"exit\" to back");
        PizzaBase pizzaBase = new PizzaBase();
        setPizzaName(pizzaBase);
        selectBaseIngredients(pizzaBase);
        selectAvailableDoughs(pizzaBase);
        selectAvailableSizes(pizzaBase);
        selectExtraIngredients(pizzaBase);
        setBasePrice(pizzaBase);
        if (!exit)
            pizzaBaseService.create(pizzaBase);
    }

    private boolean isAvailable() {
        if (pizzaSizeService.getAll().isEmpty()) {
            System.out.println("Pizza sizes is empty");
            return false;
        }
        if (pizzaDoughService.getAll().isEmpty()) {
            System.out.println("Pizza sizes is empty");
            return false;
        }
        if (pizzaIngredientService.getAll().isEmpty()) {
            System.out.println("Pizza sizes is empty");
            return false;
        }
        return true;
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

    private void selectBaseIngredients(PizzaBase pizzaBase) {
        while (!exit) {
            boolean isExit = true;
            pizzaBase.getBaseIngredients().clear();
            new ViewAllIngredientsPage().start();
            System.out.print("Please select ingredients by id separated by space: ");
            var line = InputUtils.getScanner().nextLine();
            var words = Arrays.stream(line.split(" ")).map(String::trim).collect(Collectors.toSet());
            if (words.isEmpty()) {
                System.out.println("Enter at least one value");
                continue;
            }
            for (var word : words) {
                if (word.equals("exit")) {
                    exit = true;
                    return;
                }
                try {
                    long id = Long.parseLong(word);
                    var ingredient = pizzaIngredientService.getById(id);
                    if (pizzaBase.getBaseIngredients().contains(ingredient)) {
                        System.out.println("Ingredient already added!!");
                        isExit = false;
                        break;
                    }
                    pizzaBase.getBaseIngredients().add(ingredient);
                } catch (EntityNotFoundException e) {
                    System.out.println("Ingredient by id doesn't exist");
                    isExit = false;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid value!");
                    isExit = false;
                    break;
                }
            }
            if (isExit) break;
        }
    }

    private void selectExtraIngredients(PizzaBase pizzaBase) {
        while (!exit) {
            boolean isExit = true;
            pizzaBase.getExtraIngredients().clear();
            var exceptAdded = pizzaIngredientService.getAll()
                    .stream()
                    .filter(i -> !pizzaBase.getBaseIngredients().contains(i))
                    .collect(Collectors.toList());
            if (exceptAdded.isEmpty()) {
                System.out.println("Extra available ingredients is empty");
                return;
            }
            new ViewAllIngredientsPage().printAsTable(exceptAdded);
            System.out.print("Please select extra ingredients by id separated by space: ");
            var line = InputUtils.getScanner().nextLine();
            if(line.isBlank() || line.isEmpty())
                break;
            var words = Arrays.stream(line.split(" ")).map(String::trim).collect(Collectors.toSet());
            if (words.isEmpty()) {
                break;
            }
            for (var word : words) {
                if (word.equals("exit")) {
                    exit = true;
                    return;
                }
                try {
                    long id = Long.parseLong(word);
                    var ingredient = pizzaIngredientService.getById(id);
                    if (pizzaBase.getBaseIngredients().contains(ingredient)) {
                        System.out.println("Ingredient already added in base ingredients!!");
                        isExit = false;
                        break;
                    } else if (pizzaBase.getExtraIngredients().contains(ingredient)) {
                        System.out.println("Ingredient already added!!");
                        isExit = false;
                        break;
                    }
                    pizzaBase.getExtraIngredients().add(ingredient);
                } catch (EntityNotFoundException e) {
                    System.out.println("Ingredient by id doesn't exist");
                    isExit = false;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid value!");
                    isExit = false;
                    break;
                }
            }
            if (isExit) break;
        }
    }

    private void selectAvailableSizes(PizzaBase pizzaBase) {
        while (!exit) {
            boolean isExit = true;
            pizzaBase.getPizzaSizes().clear();
            new ViewAllPizzaSizesPage().start();
            System.out.print("Please select available pizza sizes by id separated by space: ");
            var line = InputUtils.getScanner().nextLine();
            var words = Arrays.stream(line.split(" ")).map(String::trim).collect(Collectors.toSet());
            if (words.isEmpty()) {
                System.out.println("Enter at least one value");
                continue;
            }
            for (var word : words) {
                if (word.equals("exit")) {
                    exit = true;
                    return;
                }
                try {
                    long id = Long.parseLong(word);
                    var pizzaSize = pizzaSizeService.getById(id);
                    if (pizzaBase.getPizzaSizes().contains(pizzaSize)) {
                        System.out.println("Size already added!!");
                        isExit = false;
                        break;
                    }
                    pizzaBase.getPizzaSizes().add(pizzaSize);
                } catch (EntityNotFoundException e) {
                    System.out.println("Size by id doesn't exist");
                    isExit = false;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid value!");
                    isExit = false;
                    break;
                }
            }
            if (isExit) break;
        }
    }

    private void selectAvailableDoughs(PizzaBase pizzaBase) {
        while (!exit) {
            boolean isExit = true;
            pizzaBase.getPizzaDoughs().clear();
            new ViewAllPizzaDoughsPage().start();
            System.out.print("Please select available pizza doughs by id separated by space: ");
            var line = InputUtils.getScanner().nextLine();
            var words = Arrays.stream(line.split(" ")).map(String::trim).collect(Collectors.toSet());
            if (words.isEmpty()) {
                System.out.println("Enter at least one value");
                continue;
            }
            for (var word : words) {
                if (word.equals("exit")) {
                    exit = true;
                    return;
                }
                try {
                    long id = Long.parseLong(word);
                    var pizzaDough = pizzaDoughService.getById(id);
                    if (pizzaBase.getPizzaDoughs().contains(pizzaDough)) {
                        System.out.println("Dough already added!!");
                        isExit = false;
                        break;
                    }
                    pizzaBase.getPizzaDoughs().add(pizzaDough);
                } catch (EntityNotFoundException e) {
                    System.out.println("Dough by id doesn't exist");
                    isExit = false;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid value!");
                    isExit = false;
                    break;
                }
            }
            if (isExit) break;
        }
    }
}

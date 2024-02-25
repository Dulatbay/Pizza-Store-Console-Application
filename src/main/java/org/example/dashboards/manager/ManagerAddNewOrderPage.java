package org.example.dashboards.manager;

import org.example.dashboards.Page;
import org.example.dashboards.general.ViewAllIngredientsPage;
import org.example.dashboards.general.ViewAllPizzaDoughsPage;
import org.example.dashboards.general.ViewAllPizzaSizesPage;
import org.example.dashboards.general.ViewAllPizzasPage;
import org.example.entities.Order;
import org.example.entities.OrderType;
import org.example.entities.PaymentMethod;
import org.example.entities.PizzaOrder;
import org.example.entities.pizza.PizzaIngredient;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.*;
import org.example.services.impl.*;
import org.example.utils.InputUtils;

import java.time.LocalDateTime;

import static org.example.constants.ValueConstants.getValidNumber;

public class ManagerAddNewOrderPage implements Page {
    private final PizzaBaseService pizzaBaseService;
    private final PizzaSizeService pizzaSizeService;
    private final PizzaDoughService pizzaDoughService;
    private final PizzaIngredientService pizzaIngredientService;
    private final PizzaOrderService pizzaOrderService;
    private final OrderService orderService;
    private boolean isExit = false;

    public ManagerAddNewOrderPage() {
        this.pizzaOrderService = PizzaOrderServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
        this.pizzaDoughService = PizzaDoughServiceImpl.getInstance();
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
    }

    @Override
    public String getName() {
        return "Add new order";
    }

    @Override
    public void start() {
        System.out.println("Write \"exit\" to back");
        Order order = new Order();
        while (!isExit) {
            PizzaOrder pizzaOrder = new PizzaOrder();
            System.out.println("Write \"stop\" to continue fill other fields");
            try {
                System.out.println("All available pizzas:");
                new ViewAllPizzasPage().printAsTable(pizzaBaseService.getAll());


                System.out.println("Write \"stop\" to continue fill other fields");
                System.out.print("Enter a id of pizza base: ");
                var line = InputUtils.getScanner().nextLine().trim();
                if (line.equals("exit")) return;
                if (line.equals("stop")) {
                    if (order.getPizzaOrders().isEmpty()) {
                        System.out.println("Select at least one pizza!");
                        continue;
                    } else break;
                }
                ;


                var id = Long.parseLong(line);
                var pizzaBase = pizzaBaseService.getById(id);

                pizzaOrder.setPizzaBase(pizzaBase);
                setPizzaOrderSize(pizzaOrder);
                if (isExit)
                    return;
                setPizzaOrderDough(pizzaOrder);
                if (isExit)
                    return;
                setPizzaOrderIngredients(pizzaOrder);
                if (isExit)
                    return;

                while (true) {
                    System.out.print("Enter a note of pizza: ");
                    line = InputUtils.getScanner().nextLine().trim();

                    if (line.equals("exit")) {
                        return;
                    }

                    if (line.contains("[") || line.contains("]")) {
                        System.out.println("Write without [] symbols!!!");
                        continue;
                    }
                    if (line.isBlank()) {
                        System.out.println("Write a correct value!");
                        continue;
                    }
                    pizzaOrder.setNote(line);
                    break;
                }

                var sumOfAllIng = pizzaOrder.getAddedIngredients().stream().mapToDouble(PizzaIngredient::getPrice).sum()
                        + pizzaOrder.getPizzaBase().getBaseIngredients().stream().mapToDouble(PizzaIngredient::getPrice).sum();

                pizzaOrder.setTotalPrice(
                        pizzaBase.getBasePrice()
                                + pizzaOrder.getSelectedPizzaDough().getPrice()
                                + pizzaOrder.getSelectedPizzaSize().getPrice()
                                + sumOfAllIng
                );

                order.getPizzaOrders().add(pizzaOrder);
                System.out.println("Added");
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid value");
            } catch (EntityNotFoundException e) {
                System.out.println("Pizza doesn't exist");
            }
        }
        isExit = false;
        System.out.println("Please, select order type:");
        System.out.println("\n\n1) Delivery\n2) Pick up\n3) Exit");
        var number = getValidNumber(3);
        switch (number) {
            case 1: {
                order.setOrderType(OrderType.DELIVERY);
                break;
            }
            case 2: {
                order.setOrderType(OrderType.PICK_UP);
                break;
            }
            default:
                isExit = false;
        }

        if (isExit)
            return;

        System.out.println("Please, select payment method:");
        System.out.println("1) Cash\n2) Master card\n3) Exit");
        number = getValidNumber(3);
        switch (number) {
            case 1: {
                order.setPaymentMethod(PaymentMethod.CASH);
                break;
            }
            case 2: {
                order.setPaymentMethod(PaymentMethod.MASTER_CARD);
                break;
            }
            default:
                isExit = false;
        }

        if (isExit)
            return;

        while (true) {
            System.out.print("Enter a note of order: ");
            var line = InputUtils.getScanner().nextLine().trim();

            if (line.equals("exit")) {
                return;
            }

            if (line.contains("[") || line.contains("]")) {
                System.out.println("Write without [] symbols!!!");
                continue;
            }
            if (line.isBlank()) {
                System.out.println("Write a correct value!");
                continue;
            }
            order.setOrderNote(line);
            break;
        }

        while (true) {
            System.out.print("Enter a address of order: ");
            var line = InputUtils.getScanner().nextLine().trim();

            if (line.equals("exit")) {
                return;
            }

            if (line.contains("[") || line.contains("]")) {
                System.out.println("Write without [] symbols!!!");
                continue;
            }
            if (line.isBlank()) {
                System.out.println("Write a correct value!");
                continue;
            }
            order.setAddress(line);
            break;
        }

        order.setOrderDate(LocalDateTime.now());


        order.getPizzaOrders().forEach(pizzaOrderService::create);
        orderService.create(order);

        System.out.println("Created");
    }

    private void setPizzaOrderSize(PizzaOrder pizzaOrder) {
        System.out.println("All available pizza sizes:");
        new ViewAllPizzaSizesPage().printAsTable(pizzaOrder.getPizzaBase().getPizzaSizes());
        while (!isExit) {
            System.out.print("Enter a id of pizza size: ");
            try {
                var line = InputUtils.getScanner().nextLine().trim();
                if (line.equals("exit")) {
                    isExit = true;
                    return;
                }
                var id = Long.parseLong(line);
                var pizzaSize = pizzaSizeService.getById(id);
                pizzaOrder.setSelectedPizzaSize(pizzaSize);
                return;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid value");
            } catch (EntityNotFoundException exception) {
                System.out.println("Pizza size doesn't exist");
            }
        }
    }

    private void setPizzaOrderDough(PizzaOrder pizzaOrder) {
        System.out.println("All available pizza doughs:");
        new ViewAllPizzaDoughsPage().printAsTable(pizzaOrder.getPizzaBase().getPizzaDoughs());
        while (!isExit) {
            System.out.print("Enter a id of pizza dough: ");
            try {
                var line = InputUtils.getScanner().nextLine().trim();
                if (line.equals("exit")) {
                    isExit = true;
                    return;
                }
                var id = Long.parseLong(line);
                var pizzaDough = pizzaDoughService.getById(id);
                pizzaOrder.setSelectedPizzaDough(pizzaDough);
                return;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid value");
            } catch (EntityNotFoundException exception) {
                System.out.println("Pizza dough doesn't exist");
            }
        }
    }

    private void setPizzaOrderIngredients(PizzaOrder pizzaOrder) {
        System.out.println("All available pizza ingredients:");
        new ViewAllIngredientsPage().printAsTable(pizzaOrder.getPizzaBase().getExtraIngredients());
        while (!isExit) {
            System.out.print("Enter ids seperated by space: ");
            try {
                var line = InputUtils.getScanner().nextLine().trim();
                if (line.equals("exit")) {
                    isExit = true;
                    return;
                }
                if (line.isBlank() || line.isEmpty())
                    return;
                for (var idLine : line.split(" ")) {
                    var id = Long.valueOf(idLine);
                    var pizzaIngredient = pizzaIngredientService.getById(id);
                    pizzaOrder.getAddedIngredients().add(pizzaIngredient);
                }
                return;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid value");
            } catch (EntityNotFoundException exception) {
                System.out.println("Pizza dough doesn't exist");
            }
        }
    }
}

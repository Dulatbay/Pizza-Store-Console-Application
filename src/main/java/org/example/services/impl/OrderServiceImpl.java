package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.Entity;
import org.example.entities.Order;
import org.example.entities.OrderType;
import org.example.entities.PaymentMethod;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.OrderService;
import org.example.services.PizzaOrderService;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderServiceImpl implements OrderService {
    private long lastId = 0L;
    private static OrderService instance;
    private final List<Order> orders = new ArrayList<>();
    private final PizzaOrderService pizzaOrderService;

    private OrderServiceImpl() {
        this.pizzaOrderService = PizzaOrderServiceImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) instance = new OrderServiceImpl();
        return instance;
    }

    @Override
    public Order getById(Long id) throws EntityNotFoundException {
        return this.orders
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        this.orders.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<Order> getAll() {
        return this.orders;
    }

    @Override
    public void create(Order body) {
        body.setId(++lastId);
        this.orders.add(body);
        this.save();
    }

    @Override
    public void update(Long id, Order body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.ORDER_FILE;

    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var order = new Order();
                order.setId(Long.valueOf(values[0].trim()));
                order.setOrderNote(values[1].trim());
                order.setAddress(values[2].trim());
                order.setPaymentMethod(PaymentMethod.getByNumber(Integer.parseInt(values[3])));
                order.setOrderType(OrderType.getByNumber(Integer.parseInt(values[4])));
                order.setOrderDate(LocalDateTime.parse(values[5]));
                lastId = Math.max(lastId, order.getId());
                orders.add(order);
            }
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service init: " + e.getMessage());
        }
        pizzaOrdersInit();
    }

    private void pizzaOrdersInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.ORDER_PIZZA_ORDER_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                var entity = pizzaOrderService.getById(ingId);
                this.getById(pizzaId).getPizzaOrders().add(entity);
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in order service pizza orders init: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));
             BufferedWriter writerToIngredientsFile = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.ORDER_PIZZA_ORDER_FILE))
        ) {
            for (var i : this.orders) {
                writer.write(i.fileToString());
                secondarySave(i.getId(), i.getPizzaOrders().stream().map(j -> (Entity) j).toList(), writerToIngredientsFile);
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }

    private void secondarySave(Long id, List<Entity> entities, BufferedWriter writerToIngredientsFile) throws IOException {
        for (var entity : entities) {
            writerToIngredientsFile.write(String.format("%s|%s%n", id, entity.getId()));
        }
    }
}

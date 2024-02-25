package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.Entity;
import org.example.entities.PizzaOrder;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PizzaOrderServiceImpl implements PizzaOrderService {
    private static PizzaOrderService instance;
    private final List<PizzaOrder> pizzaOrders = new ArrayList<>();
    private long lastId = 0L;
    private final PizzaDoughService pizzaDoughService;
    private final PizzaIngredientService pizzaIngredientService;
    private final PizzaSizeService pizzaSizeService;
    private final PizzaBaseService pizzaBaseService;

    private PizzaOrderServiceImpl() {
        this.pizzaBaseService = PizzaBaseServiceImpl.getInstance();
        this.pizzaDoughService = PizzaDoughServiceImpl.getInstance();
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
    }

    public static PizzaOrderService getInstance() {
        if (instance == null) instance = new PizzaOrderServiceImpl();
        return instance;
    }

    @Override
    public PizzaOrder getById(Long id) throws EntityNotFoundException {
        return this.pizzaOrders
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        this.pizzaOrders.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<PizzaOrder> getAll() {
        return this.pizzaOrders;
    }

    @Override
    public void create(PizzaOrder body) {
        body.setId(++lastId);
        this.pizzaOrders.add(body);
        this.save();
    }

    @Override
    public void update(Long id, PizzaOrder body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.PIZZA_ORDER_FILE;
    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaOrder = new PizzaOrder();
                pizzaOrder.setId(Long.valueOf(values[0].trim()));
                pizzaOrder.setNote(values[1].trim());
                pizzaOrder.setTotalPrice(Double.valueOf(values[2].trim()));

                var pizzaBaseId = Long.parseLong(values[3].trim());
                pizzaOrder.setPizzaBase(pizzaBaseService.getById(pizzaBaseId));

                var sizeId = Long.parseLong(values[4].trim());
                pizzaOrder.setSelectedPizzaSize(pizzaSizeService.getById(sizeId));

                var doughId = Long.parseLong(values[5].trim());
                pizzaOrder.setSelectedPizzaDough(pizzaDoughService.getById(doughId));

                lastId = Math.max(lastId, pizzaOrder.getId());
                pizzaOrders.add(pizzaOrder);
            }
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service init: " + e.getMessage());
        }
        addedIngredientsInit();
    }

    private void addedIngredientsInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.PIZZA_ORDER_ADDED_INGREDIENTS_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                this.getById(pizzaId).getAddedIngredients().add(pizzaIngredientService.getById(ingId));
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in pizza order service ingredients init: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));) {
            for (var i : this.pizzaOrders) {
                writer.write(i.fileToString());
                secondarySave(i.getId(), i.getAddedIngredients().stream().map(j -> (Entity) j).toList());
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }

    private void secondarySave(Long pizzaOrderId, List<Entity> entities) {
        try (var writerToIngredientsFile = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_ORDER_ADDED_INGREDIENTS_FILE))) {
            for (var entity : entities) {
                writerToIngredientsFile.write(String.format("%s|%s%n", pizzaOrderId, entity.getId()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

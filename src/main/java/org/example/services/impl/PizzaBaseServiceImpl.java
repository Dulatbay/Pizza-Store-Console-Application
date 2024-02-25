package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.Entity;
import org.example.entities.pizza.PizzaBase;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaBaseService;
import org.example.services.PizzaDoughService;
import org.example.services.PizzaIngredientService;
import org.example.services.PizzaSizeService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PizzaBaseServiceImpl implements PizzaBaseService {
    private static PizzaBaseService instance;
    private final List<PizzaBase> pizzaBases = new ArrayList<>();
    private final PizzaIngredientService pizzaIngredientService;
    private final PizzaSizeService pizzaSizeService;
    private final PizzaDoughService pizzaDoughService;
    private Long lastId = 0L;

    private PizzaBaseServiceImpl() {
        this.pizzaSizeService = PizzaSizeServiceImpl.getInstance();
        this.pizzaDoughService = PizzaDoughServiceImpl.getInstance();
        this.pizzaIngredientService = PizzaIngredientServiceImpl.getInstance();
    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaBase = new PizzaBase();
                pizzaBase.setId(Long.valueOf(values[0].trim()));
                pizzaBase.setName(values[1].trim());
                pizzaBase.setBasePrice(Double.valueOf(values[2].trim()));
                lastId = Math.max(lastId, pizzaBase.getId());
                pizzaBases.add(pizzaBase);
            }
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service init: " + e.getMessage());
        }
        baseIngredientsInit();
        extraIngredientsInit();
        pizzaSizeInit();
        pizzaDoughInit();
    }

    private void baseIngredientsInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_INGREDIENTS_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                this.getById(pizzaId).getBaseIngredients().add(pizzaIngredientService.getById(ingId));
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service ingredients init: " + e.getMessage());
        }
    }

    private void extraIngredientsInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_EXTRA_INGREDIENTS_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                this.getById(pizzaId).getExtraIngredients().add(pizzaIngredientService.getById(ingId));
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service extra ingredients init: " + e.getMessage());
        }
    }

    private void pizzaSizeInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_SIZE_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                this.getById(pizzaId).getPizzaSizes().add(pizzaSizeService.getById(ingId));
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service sizes init: " + e.getMessage());
        }
    }

    private void pizzaDoughInit() {
        try {
            var file = new File(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_DOUGH_FILE);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaId = Long.parseLong(values[0]);
                var ingId = Long.parseLong(values[1]);
                this.getById(pizzaId).getPizzaDoughs().add(pizzaDoughService.getById(ingId));
            }
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            System.out.println("Some error occurred in pizza base service sizes init: " + e.getMessage());
        }
    }


    @Override
    public void save() {
        try (BufferedWriter baseIngredientsWriter = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_INGREDIENTS_FILE));
             BufferedWriter pizzaBaseWriter = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_FILE));
             BufferedWriter baseDoughsWriter = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_DOUGH_FILE));
             BufferedWriter baseSizesWriter = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_SIZE_FILE));
             BufferedWriter extraIngredientsWriter = new BufferedWriter(new FileWriter(ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_EXTRA_INGREDIENTS_FILE));
        ) {
            for (var i : this.pizzaBases) {
                pizzaBaseWriter.write(i.fileToString());
                secondarySave(i.getId(), i.getBaseIngredients().stream().map(j -> (Entity) j).toList(), baseIngredientsWriter);
                secondarySave(i.getId(), i.getPizzaDoughs().stream().map(j -> (Entity) j).toList(), baseDoughsWriter);
                secondarySave(i.getId(), i.getPizzaSizes().stream().map(j -> (Entity) j).toList(), baseSizesWriter);
                secondarySave(i.getId(), i.getExtraIngredients().stream().map(j -> (Entity) j).toList(), extraIngredientsWriter);
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }

    }

    private void secondarySave(Long pizzaBaseId, List<Entity> entities, BufferedWriter writer) throws IOException {
        for (var ingredient : entities) {
            writer.write(String.format("%s|%s%n", pizzaBaseId, ingredient.getId()));
        }
    }

    public static PizzaBaseService getInstance() {
        if (instance == null) instance = new PizzaBaseServiceImpl();
        return instance;
    }

    @Override
    public PizzaBase getById(Long id) throws EntityNotFoundException {
        return pizzaBases
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        pizzaBases.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<PizzaBase> getAll() {
        return this.pizzaBases;
    }

    @Override
    public void create(PizzaBase body) {
        body.setId(++lastId);
        this.pizzaBases.add(body);
        this.save();
    }

    @Override
    public void update(Long id, PizzaBase body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.PIZZA_BASE_FILE;
    }

    @Override
    public Optional<PizzaBase> getByName(String name) {
        return this.pizzaBases.stream().filter(i -> i.getName().equals(name)).findFirst();
    }

    @Override
    public List<PizzaBase> findByName(String name) {
        return this.pizzaBases.stream().filter(i -> i.getName().contains(name)).toList();
    }
}

package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.pizza.PizzaIngredient;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaIngredientService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PizzaIngredientServiceImpl implements PizzaIngredientService {
    private static PizzaIngredientService instance;
    private final List<PizzaIngredient> pizzaIngredients = new ArrayList<>();

    private Long lastId = 0L;

    private PizzaIngredientServiceImpl() {
    }

    @Override
    public PizzaIngredient getById(Long id) throws EntityNotFoundException {
        return this.pizzaIngredients.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        this.pizzaIngredients.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<PizzaIngredient> getAll() {
        return this.pizzaIngredients;
    }

    @Override
    public void create(PizzaIngredient body) {
        body.setId(++lastId);
        this.pizzaIngredients.add(body);
        this.save();
    }

    @Override
    public void update(Long id, PizzaIngredient body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.PIZZA_INGREDIENT_FILE;

    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setId(Long.valueOf(values[0].trim()));
                pizzaIngredient.setDescription(values[1].trim());
                pizzaIngredient.setPrice(Double.valueOf(values[2].trim()));
                lastId = Math.max(lastId, pizzaIngredient.getId());
                pizzaIngredients.add(pizzaIngredient);
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));) {
            for (var i : this.pizzaIngredients) {
                writer.write(i.fileToString());
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }

    public static PizzaIngredientService getInstance() {
        if (instance == null) instance = new PizzaIngredientServiceImpl();
        return instance;
    }

    @Override
    public Optional<PizzaIngredient> getByDescription(String description) {
        return this.pizzaIngredients.stream().filter(i -> i.getDescription().equals(description)).findAny();
    }
}

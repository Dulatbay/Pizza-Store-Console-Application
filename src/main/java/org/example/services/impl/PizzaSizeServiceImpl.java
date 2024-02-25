package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.pizza.PizzaSize;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaSizeService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PizzaSizeServiceImpl implements PizzaSizeService {
    private final List<PizzaSize> pizzaSizes = new ArrayList<>();
    private Long lastId = 0L;
    private static PizzaSizeService instance;

    private PizzaSizeServiceImpl() {
    }

    @Override
    public PizzaSize getById(Long id) throws EntityNotFoundException {
        return this.pizzaSizes.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        this.pizzaSizes.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<PizzaSize> getAll() {
        return this.pizzaSizes;
    }

    @Override
    public void create(PizzaSize body) {
        body.setId(++lastId);
        this.pizzaSizes.add(body);
        this.save();
    }

    @Override
    public void update(Long id, PizzaSize body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.PIZZA_SIZE_FILE;
    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                var pizzaSize = new PizzaSize();
                pizzaSize.setId(Long.valueOf(values[0].trim()));
                pizzaSize.setDescription(values[1].trim());
                pizzaSize.setCentimeter(Integer.valueOf(values[2].trim()));
                pizzaSize.setPrice(Double.valueOf(values[3].trim()));
                lastId++;
                pizzaSizes.add(pizzaSize);
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));) {
            for (var i : this.pizzaSizes) {
                writer.write(i.fileToString());
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }

    public static PizzaSizeService getInstance() {
        if (instance == null) instance = new PizzaSizeServiceImpl();
        return instance;
    }

    @Override
    public Optional<PizzaSize> getByDescription(String description) {
        return this.pizzaSizes
                .stream()
                .filter(i -> i.getDescription().equals(description))
                .findFirst();
    }
}

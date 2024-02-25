package org.example.services.impl;

import org.example.constants.ValueConstants;
import org.example.entities.pizza.PizzaDough;
import org.example.exceptions.EntityNotFoundException;
import org.example.services.PizzaDoughService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PizzaDoughServiceImpl implements PizzaDoughService {
    private final List<PizzaDough> pizzaDoughs = new ArrayList<>();
    private Long lastId = 0L;
    private static PizzaDoughService instance;

    private PizzaDoughServiceImpl() {
    }

    @Override
    public PizzaDough getById(Long id) throws EntityNotFoundException {
        return this.pizzaDoughs.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        this.pizzaDoughs.remove(this.getById(id));
        this.save();
    }

    @Override
    public List<PizzaDough> getAll() {
        return this.pizzaDoughs;
    }

    @Override
    public void create(PizzaDough body) {
        body.setId(++lastId);
        this.pizzaDoughs.add(body);
        this.save();
    }

    @Override
    public void update(Long id, PizzaDough body) throws EntityNotFoundException {
        deleteById(id);
        body.setId(id);
        this.create(body);
        this.save();
    }

    @Override
    public String getFileName() {
        return ValueConstants.DB_DIR + ValueConstants.PIZZA_DOUGH_FILE;
    }

    @Override
    public void init() {
        try {
            File file = new File(this.getFileName());
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                var values = sc.nextLine().split("\\|");
                PizzaDough pizzaDough = new PizzaDough();
                pizzaDough.setId(Long.valueOf(values[0].trim()));
                pizzaDough.setDescription(values[1].trim());
                pizzaDough.setPrice(Double.valueOf(values[2].trim()));
                lastId++;
                pizzaDoughs.add(pizzaDough);
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));) {
            for (var i : this.pizzaDoughs) {
                writer.write(i.fileToString());
            }
        } catch (Exception ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }

    public static PizzaDoughService getInstance() {
        if (instance == null) instance = new PizzaDoughServiceImpl();
        return instance;
    }

    @Override
    public Optional<PizzaDough> getByDescription(String description) {
        return this.pizzaDoughs.stream().filter(i -> i.getDescription().equals(description)).findAny();
    }
}

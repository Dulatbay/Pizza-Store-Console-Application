package org.example.services;

import org.example.entities.pizza.PizzaIngredient;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

import java.util.Optional;

public interface PizzaIngredientService extends ServiceAsDb, CrudService<PizzaIngredient> {
    Optional<PizzaIngredient> getByDescription(String line);
}

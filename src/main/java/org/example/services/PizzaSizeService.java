package org.example.services;

import org.example.entities.pizza.PizzaSize;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

import java.util.Optional;

public interface PizzaSizeService extends ServiceAsDb, CrudService<PizzaSize> {
    Optional<PizzaSize> getByDescription(String description);
}

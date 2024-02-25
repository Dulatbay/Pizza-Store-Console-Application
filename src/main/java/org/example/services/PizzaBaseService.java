package org.example.services;

import org.example.entities.pizza.PizzaBase;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

import java.util.List;
import java.util.Optional;

public interface PizzaBaseService extends ServiceAsDb, CrudService<PizzaBase> {
    Optional<PizzaBase> getByName(String name);
    List<PizzaBase> findByName(String name);
}

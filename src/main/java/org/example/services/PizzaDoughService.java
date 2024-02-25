package org.example.services;

import org.example.entities.pizza.PizzaDough;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

import java.util.Optional;

public interface PizzaDoughService extends ServiceAsDb, CrudService<PizzaDough>{
    Optional<PizzaDough> getByDescription(String description);
}

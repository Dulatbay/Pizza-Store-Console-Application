package org.example.services;

import org.example.entities.PizzaOrder;
import org.example.entities.pizza.PizzaDough;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

import java.util.Optional;

public interface PizzaOrderService extends ServiceAsDb, CrudService<PizzaOrder>{
}

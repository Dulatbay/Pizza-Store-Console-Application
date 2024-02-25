package org.example.services;

import org.example.entities.Order;
import org.example.entities.PizzaOrder;
import org.example.services.base.CrudService;
import org.example.services.base.ServiceAsDb;

public interface OrderService extends ServiceAsDb, CrudService<Order>{
}

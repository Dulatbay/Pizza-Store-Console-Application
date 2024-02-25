package org.example.entities;

import org.example.entities.pizza.PizzaBase;
import org.example.entities.pizza.PizzaDough;
import org.example.entities.pizza.PizzaIngredient;
import org.example.entities.pizza.PizzaSize;

import java.util.ArrayList;
import java.util.List;

public class PizzaOrder implements Entity {
    private Long id;
    private Double totalPrice;
    private String note;
    private PizzaBase pizzaBase;
    private PizzaSize selectedPizzaSize;
    private PizzaDough selectedPizzaDough;
    private List<PizzaIngredient> addedIngredients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PizzaBase getPizzaBase() {
        return pizzaBase;
    }

    public void setPizzaBase(PizzaBase pizzaBase) {
        this.pizzaBase = pizzaBase;
    }

    public PizzaSize getSelectedPizzaSize() {
        return selectedPizzaSize;
    }

    public void setSelectedPizzaSize(PizzaSize selectedPizzaSize) {
        this.selectedPizzaSize = selectedPizzaSize;
    }

    public PizzaDough getSelectedPizzaDough() {
        return selectedPizzaDough;
    }

    public void setSelectedPizzaDough(PizzaDough selectedPizzaDough) {
        this.selectedPizzaDough = selectedPizzaDough;
    }

    public List<PizzaIngredient> getAddedIngredients() {
        return addedIngredients;
    }

    public void setAddedIngredients(List<PizzaIngredient> addedIngredients) {
        this.addedIngredients = addedIngredients;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String fileToString() {
        return String.format("%d|%s|%s|%d|%d|%d%n", this.id, this.note, this.totalPrice, this.pizzaBase.getId(), this.selectedPizzaSize.getId(), this.selectedPizzaDough.getId());
    }
}

package org.example.entities.pizza;

import org.example.entities.Entity;
import org.example.entities.FileToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PizzaBase implements Entity {
    private Long id;
    private String name;
    private Double basePrice;
    List<PizzaDough> pizzaDoughs = new ArrayList<>();
    List<PizzaSize> pizzaSizes = new ArrayList<>();
    List<PizzaIngredient> baseIngredients = new ArrayList<>();
    List<PizzaIngredient> extraIngredients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PizzaDough> getPizzaDoughs() {
        return pizzaDoughs;
    }

    public void setPizzaDoughs(List<PizzaDough> pizzaDoughs) {
        this.pizzaDoughs = pizzaDoughs;
    }

    public List<PizzaSize> getPizzaSizes() {
        return pizzaSizes;
    }

    public void setPizzaSizes(List<PizzaSize> pizzaSizes) {
        this.pizzaSizes = pizzaSizes;
    }

    public List<PizzaIngredient> getBaseIngredients() {
        return baseIngredients;
    }

    public void setBaseIngredients(List<PizzaIngredient> baseIngredients) {
        this.baseIngredients = baseIngredients;
    }

    public List<PizzaIngredient> getExtraIngredients() {
        return extraIngredients;
    }

    public void setExtraIngredients(List<PizzaIngredient> extraIngredients) {
        this.extraIngredients = extraIngredients;
    }


    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", this.id, this.name, this.basePrice);
    }

    @Override
    public String fileToString() {
        return String.format("%s|%s|%s%n", this.id, this.name, this.basePrice);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        PizzaBase pizzaBase = (PizzaBase) obj;
        return Objects.equals(pizzaBase.getId(), this.id);
    }
}
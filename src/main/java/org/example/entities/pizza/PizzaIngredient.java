package org.example.entities.pizza;

import org.example.entities.Entity;
import org.example.entities.FileToString;

public class PizzaIngredient implements Entity {
    private Long id;
    private String description;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String fileToString() {
        return String.format("%s|%s|%s%n", this.id, this.description, this.price);
    }
}

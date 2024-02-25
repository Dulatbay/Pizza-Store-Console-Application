package org.example.entities.pizza;

import org.example.entities.Entity;

public class PizzaSize implements Entity {
    private Long id;
    private String description;
    private Integer centimeter;
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

    public Integer getCentimeter() {
        return centimeter;
    }

    public void setCentimeter(Integer centimeter) {
        this.centimeter = centimeter;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String fileToString() {
        return String.format("%d|%s|%s|%s%n", id, description, centimeter, price);
    }
}

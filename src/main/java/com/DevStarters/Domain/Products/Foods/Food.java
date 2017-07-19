package com.DevStarters.Domain.Products.Foods;

import com.DevStarters.Domain.Products.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;

public class Food extends Product {
    private HashSet<Product> ingredients;
    private String cousine;

    public Food() {
        ingredients = new HashSet<>();
        cousine = "none";
    }

    public Food(String name, double price, String vendor, Period periodOfValidity,
                HashSet<Product> ingredients, String cousine) {
        super(name, price, vendor, periodOfValidity);
        ingredients = new HashSet<>();
        this.cousine = cousine;
    }

    public Food(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate,
                HashSet<Product> ingredients, String cousine) {
        super(name, price, vendor, productionDate, expirationDate);
        ingredients = new HashSet<>();
        this.cousine = cousine;
    }

    public HashSet<Product> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashSet<Product> ingredients) {
        this.ingredients = ingredients;
    }
}

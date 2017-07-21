package com.DevStarters.Domain.Products.Foods;

import com.DevStarters.Domain.Products.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;

public class Food extends Product {

    private HashSet<Product> ingredients;
    private String kitchen;

    public Food() {
        ingredients = new HashSet<>();
        kitchen = "none";
    }

    public Food(String name, double price, String vendor, Period periodOfValidity, String kitchen) {
        super(name, price, vendor, periodOfValidity);
        ingredients = new HashSet<>();
        this.kitchen = kitchen;
    }

    public Food(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate, String kitchen) {
        super(name, price, vendor, productionDate, expirationDate);
        ingredients = new HashSet<>();
        this.kitchen = kitchen;
    }

    public HashSet<Product> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashSet<Product> ingredients) {
        this.ingredients = ingredients;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public void setExpirationDate() {
        LocalDate date = getProductionDate().plus(Period.ofDays(5));
        for (Product product : ingredients) {
            if (product.getExpirationDate().isBefore(date)) date = product.getExpirationDate();
        }
        setExpirationDate(date);
    }

    public void addProductToFood(Product product){
        ingredients.add(product);
    }

    public Product removeProductFromFood(Product product){
        ingredients.remove(product);
        return (ingredients.contains(product)) ? product : null;
    }
}

package com.DevStarters.Domain.Products.Foods;

import com.DevStarters.Domain.Products.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;

public class Barbecue extends Food{
    public Barbecue() {
        super();
        setKitchen("American");
    }

    public Barbecue(String name, double price, String vendor, Period periodOfValidity, HashSet<Product> ingredients) {
        super(name, price, vendor, periodOfValidity, ingredients, "American");
    }

    public Barbecue(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate,
                    HashSet<Product> ingredients) {
        super(name, price, vendor, productionDate, expirationDate, ingredients, "American");
    }
}

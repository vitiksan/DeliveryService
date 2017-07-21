package com.DevStarters.Domain.Products.Foods;

import com.DevStarters.Domain.Products.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;

public class Pizza extends Food{

    public Pizza() {
        super();
        setKitchen("Italian");
    }

    public Pizza(String name, double price, String vendor, Period periodOfValidity, HashSet<Product> ingredients) {
        super(name, price, vendor, periodOfValidity, ingredients,"Italian");
    }

    public Pizza(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate,
                 HashSet<Product> ingredients) {
        super(name, price, vendor, productionDate, expirationDate, ingredients, "Italian");
    }
}

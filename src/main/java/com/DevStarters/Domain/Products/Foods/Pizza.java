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

    public Pizza(String name, double price, String vendor, Period periodOfValidity) {
        super(name, price, vendor, periodOfValidity,new HashSet<Product>(),"Italian");
    }

    public Pizza(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate) {
        super(name, price, vendor, productionDate, expirationDate, new HashSet<Product>(), "Italian");
    }
}

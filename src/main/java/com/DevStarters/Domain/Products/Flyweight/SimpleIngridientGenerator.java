package com.DevStarters.Domain.Products.Flyweight;// Created by on 19.07.2017.

import com.DevStarters.Domain.Products.Product;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

public class SimpleIngridientGenerator {
    private static Set<Product> ingredients = new HashSet<Product>();

    public static Product getIngridient(String name,double price){
        if (!ingredients.contains(new Product(name,price,"none", Period.ofDays(2))))
        ingredients.add(new Product(name,price,"none", Period.ofDays(2)));
        for (Product product: ingredients){
            if (product.getName().equals(name) && product.getPrice()==price)
                return product;
        }
        return null;
    }
}

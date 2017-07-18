package com.DevStarters.Domain.Products;

import java.time.LocalDate;
import java.time.Period;

public class Product {
    private int id;
    private String name;
    private double price;
    private String vendor;
    private LocalDate productionDate;
    private LocalDate expirationDate;

    public Product() {
        name = "none";
        price = 0;
        vendor = "none";
        productionDate = LocalDate.now();
        expirationDate = LocalDate.now();
    }

    public Product(String name, double price, String vendor, Period periodOfValidity) {
        this.name = name;
        this.price = price;
        this.vendor = vendor;
        productionDate = LocalDate.now();
        expirationDate = productionDate.plus(periodOfValidity);
    }

    public Product(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate) {
        this.name = name;
        this.price = price;
        this.vendor = vendor;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}

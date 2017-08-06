package com.DevStarters.Domain;

import com.DevStarters.DAO.Identificator;

import java.time.LocalDate;
import java.time.Period;

public class Product implements Identificator<Integer> {
    private int id;
    private String name;
    private double price;
    private String description;
    private int vendorId;
    private LocalDate productionDate;
    private LocalDate expirationDate;

    public Product() {
        name = "none";
        price = 0;
        vendorId = 0;
        productionDate = LocalDate.now();
        expirationDate = LocalDate.now();
    }

    public Product(String name, double price, String description, int vendorId, Period periodOfValidity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.vendorId = vendorId;
        productionDate = LocalDate.now();
        expirationDate = productionDate.plus(periodOfValidity);
    }

    public Product(String name, double price, String description, int vendorId, LocalDate productionDate, LocalDate expirationDate) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.vendorId = vendorId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (vendorId != product.vendorId) return false;
        return name != null ? name.equals(product.name) : product.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + vendorId;
        return result;
    }

    @Override
    public String toString() {
        return "Product: " +
                "\nProduct id: "+ id+
                "\nName: " + name +
                "\nPrice: " + price +
                "\nDescription: " + description +
                "\nVendor id: " + vendorId +
                "\nProduction date: " + productionDate.getDayOfMonth() + "."
                + productionDate.getMonth() + "." + productionDate.getYear() +
                "\nExpiration date: " + +expirationDate.getDayOfMonth() + "."
                + expirationDate.getMonth() + "." + expirationDate.getYear();
    }
}

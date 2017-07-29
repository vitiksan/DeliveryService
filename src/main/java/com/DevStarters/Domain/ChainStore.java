package com.DevStarters.Domain;

import com.DevStarters.DAO.Identificator;
import com.DevStarters.Support.CardNumberGenerator;

import java.util.HashSet;
import java.util.Scanner;

public class ChainStore implements Identificator<Integer> {
    private int id;
    private String name;
    private String description;
    private String address;
    private String kitchen;
    private String type;
    private String cardForPayments;
    private HashSet<Product> products;

    public ChainStore() {
        id = 0;
        name = "none";
        description = "none";
        address = "none";
        kitchen = "none";
        type="none";
        this.cardForPayments = CardNumberGenerator.generateVCNumber();
        products = new HashSet<>();
    }

    public ChainStore(int id, String name, String description, String address,
                      String kitchen,String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.kitchen = kitchen;
        this.type=type;
        this.cardForPayments = CardNumberGenerator.generateVCNumber();
        products = new HashSet<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addItem(Product product) {
        products.add(product);
    }

    public String getCardForPayments() {
        return cardForPayments;
    }

    public void setCardForPayments(String cardForPayments) {
        this.cardForPayments = cardForPayments;
    }

    public HashSet<Product> getProducts() {
        return products;
    }

    public void setProducts(HashSet<Product> products) {
        this.products = products;
    }

    public Product removeItem() {
        Scanner in = new Scanner(System.in);
        Product temp = null;
        for (Product product : products) {
            System.out.println("Enter id of product which you want to delete: ");
        }
        int id = Integer.parseInt(in.next());
        for (Product product : products) {
            if (id == product.getId()) {
                temp = product;
            }
        }
        return temp;
    }

    public Product removeItem(Product product) {
        boolean temp = products.remove(product);
        return (temp) ? product : null;
    }
}

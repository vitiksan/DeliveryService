package com.DevStarters.Domain;

import com.DevStarters.DAO.Identificator;
import com.DevStarters.Util.CardNumberGenerator;

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
        type = "none";
        this.cardForPayments = CardNumberGenerator.generateVCNumber();
        products = new HashSet<>();
    }

    public ChainStore(String name, String description, String address,
                      String kitchen, String type) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.kitchen = kitchen;
        this.type = type;
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

    public Product removeItem() {
        Scanner in = new Scanner(System.in);
        Product temp = null;
        for (Product product : products) {
            System.out.println(product.toString());
        }
        try {
            System.out.println("Enter id of product which you want to delete: ");
            int id = Integer.parseInt(in.next());
            for (Product product : products) {
                if (id == product.getId()) {
                    temp = product;
                }
            }
            if (temp == null) throw new Exception("Not found this id");
        } catch (NumberFormatException nfe) {
            id = 0;
            System.out.println("Error with input id: " + nfe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    @Override
    public String toString() {
        return "ChainStore: " +
                "\n id: " + id +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nAddress: " + address +
                "\nKitchen: " + kitchen +
                "\nType: " + type +
                "\nCard for payments: " + cardForPayments;
    }
}

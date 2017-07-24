package com.DevStarters.Domain.ChainStores;

import com.DevStarters.Domain.Products.Product;

import java.util.HashSet;
import java.util.Scanner;

public class Shop implements IChainStore {
    private int id;
    private String name;
    private String description;
    private String address;
    private String kitchen;
    private String type;
    private HashSet<Product> items;

    public Shop() {
        id=0;
        name="none";
        description="none";
        address="none";
        kitchen="none";
        type="none";
        items=new HashSet<>();
    }

    public Shop(String name, String description, String address, String kitchen, String type) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.kitchen = kitchen;
        this.type = type;
        items=new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getKitchen() {
        return kitchen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public void addItem(Product product) {
        items.add(product);
    }

    @Override
    public Product removeItem() {
        Scanner in = new Scanner(System.in);
        Product temp = null;
        for (Product item : items) {
            System.out.println(item.toString());
        }
        System.out.println("Enter id product which you want to delete: ");
        int id = Integer.parseInt(in.next());
        for (Product item : items) {
            if (item.getId() == id) {
                temp = item;
                items.remove(item);
            }
        }
        return temp;
    }

    @Override
    public Product removeItem(Product product) {
        boolean temp = items.remove(product);
        return (temp) ? product : null;
    }

}

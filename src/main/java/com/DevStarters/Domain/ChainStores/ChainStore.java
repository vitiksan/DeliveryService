package com.DevStarters.Domain.ChainStores;

import com.DevStarters.Domain.Products.Food;
import com.DevStarters.Domain.Products.Product;

import java.util.HashSet;
import java.util.Scanner;

public class ChainStore implements IChainStore {
    private int id;
    private String name;
    private String description;
    private String address;
    private String kitchen;
    private String type;
    private HashSet<Food> foods;

    public ChainStore() {
        id = 0;
        name = "none";
        description = "none";
        address = "none";
        kitchen = "none";
        type="none";
        foods = new HashSet<>();
    }

    public ChainStore(int id, String name, String description, String address,
                      String kitchen,String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.kitchen = kitchen;
        this.type=type;
        foods = new HashSet<>();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getAddress() {
        return address;
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
    public void addItem(Product product) {
        foods.add((Food) product);
    }

    @Override
    public Product removeItem() {
        Scanner in = new Scanner(System.in);
        Food temp = null;
        for (Food food : foods) {
            System.out.println("Enter id of food which you want to delete: ");
        }
        int id = Integer.parseInt(in.next());
        for (Food food : foods) {
            if (id == food.getId()) {
                temp = food;
            }
        }
        return temp;
    }

    @Override
    public Product removeItem(Product product) {
        boolean temp = foods.remove(product);
        return (temp) ? product : null;
    }
}

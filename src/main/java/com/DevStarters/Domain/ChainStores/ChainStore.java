package com.DevStarters.Domain.ChainStores;

import com.DevStarters.Domain.Products.Product;

public interface ChainStore {
    void setId(int id);
    void setName(String name);
    void setDescription(String description);
    void setAddress(String address);
    void setKitchen(String kitchen);
    int getId();
    String getName();
    String getDescription();
    String getAddress();
    String getKitchen();
    void addItem(Product product);
    Product removeItem();
    Product removeItem(Product product);

}

package com.DevStarters.Domain;

import com.DevStarters.DAO.Identificator;

public class OrderLine implements Identificator<Integer>{

    private int orderId;
    private Product product;
    private double price;
    private int count;

    public OrderLine() {
    }

    public OrderLine(int orderId, Product product,int count) {
        this.orderId = orderId;
        this.product = product;
        this.count = count;
        price=product.getPrice()* count;
    }

    @Override
    public int getId() {
        return 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

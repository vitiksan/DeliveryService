package com.DevStarters.Domain.Order;

import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.PaymentSystem.Transaction;

import java.util.HashSet;
import java.util.LinkedList;

public class Order implements Identificator<Integer>{

    private int orderId;
    private int userId;
    private HashSet<OrderLine> lines;
    private LinkedList<Transaction>transactions;
    private double price;
    private String status;

    public Order() {
        orderId=0;
        status="none";
        userId=0;
        price=0;
        lines=new HashSet<>();
        transactions=new LinkedList<>();
    }

    public Order(int userId) {
        this.userId = userId;
        lines=new HashSet<>();
        transactions=new LinkedList<>();
        this.price=allPrice();
        status="not executed";
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashSet<OrderLine> getLines() {
        return lines;
    }

    public void setLines(HashSet<OrderLine> lines) {
        this.lines = lines;
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double allPrice(){
        double temp=0;
        for(OrderLine line:lines){
            temp+=line.getPrice();
        }
        return  temp;
    }

    public void addNewLine(OrderLine line){
        lines.add(line);
        this.price=allPrice();
    }

    public void addNewTransaction(OrderLine line){
        for(Transaction transaction:transactions){

            //TODO
        }
    }
}

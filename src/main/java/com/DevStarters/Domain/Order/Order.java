package com.DevStarters.Domain.Order;

import com.DevStarters.DAO.DaoExeption;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.DAO.IGenDao;
import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.OrderLine;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import com.DevStarters.MySql.MySqlDaoFactory;

import java.util.HashSet;
import java.util.LinkedList;

public class Order implements Identificator<Integer> {
    private int id;
    private int userId;
    private HashSet<OrderLine> lines;
    private LinkedList<Transaction> transactions;
    private double price;
    private String status;

    public Order() {
        id = 0;
        status = "none";
        userId = 0;
        price = 0;
        lines = new HashSet<OrderLine>();
        transactions = new LinkedList<>();
    }

    public Order(int userId) {
        this.userId = userId;
        lines = new HashSet<>();
        transactions = new LinkedList<>();
        this.price = allPrice();
        status = "not executed";
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double allPrice() {
        double temp = 0;
        for (OrderLine line : lines) {
            temp += line.getPrice();
        }
        return temp;
    }

    public void addNewLine(OrderLine line) {
        boolean temp = false;
        for (OrderLine orderLine : lines) {
            if (orderLine.getProduct().getId() == line.getProduct().getId()) {
                orderLine.setCount(orderLine.getCount() + line.getCount());
                temp = true;
            }
        }
        if (!temp) lines.add(line);
        this.price = allPrice();
    }

    public void addNewTransaction(int accountId, OrderLine line) {
        int id = line.getProduct().getVendorId();
        boolean temp = false;
        DaoFactory factory = new MySqlDaoFactory();
        try {
            IGenDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            ChainStore shop = (ChainStore) dao.read(id);
            for (Transaction transaction : transactions) {
                if (shop.getCardForPayments().equals(transaction.getRecipientCard())) {
                    transaction.setAmount(transaction.getAmount() + line.getPrice());
                    temp = true;
                }
            }
            if (!temp) transactions.add(new Transaction(accountId, shop.getCardForPayments(), line.getPrice()));
        } catch (DaoExeption daoExeption) {
            System.out.println("False transaction in order :" + daoExeption.getMessage());
        }
    }
}
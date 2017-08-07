package com.DevStarters.Domain.Order;

import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.DAO.IGenDao;
import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.PaymentSystem.Account;
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
        status = "not executed";
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
        try {
            DaoFactory factory = new MySqlDaoFactory();
            IGenDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
            for (OrderLine orderLine : lines) {
                if (orderLine.getProduct().getId() == line.getProduct().getId()) {
                    orderLine.setCount(orderLine.getCount() + line.getCount());
                    dao.update(orderLine);
                    temp = true;
                }
            }
            if (!temp) lines.add((OrderLine) dao.create(line));
            this.price = allPrice();
            dao = factory.getDao(factory.getConnection(), Order.class);
            dao.update(this);
        } catch (DaoException e) {
            System.out.println("Error with add new line to order: " + e.getMessage());
        }
    }

    public void addTransactions(Account account) {
        try {
            DaoFactory factory = new MySqlDaoFactory();
            IGenDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            for (OrderLine line : lines) {
                boolean temp = false;
                ChainStore shop = (ChainStore) dao.read(line.getProduct().getVendorId());
                for (Transaction item : transactions) {
                    if (shop.getCardForPayments().equals(item.getRecipientCard())) {
                        item.setAmount(item.getAmount() + line.getPrice());
                        dao.update(item);
                        temp = true;
                    }
                }
                if (!temp) {
                    transactions.add((Transaction) dao.create(new Transaction(account.getId(),
                            shop.getCardForPayments(), line.getPrice())));
                }
            }
        } catch (DaoException daoException) {
            System.out.println("Error with send many :" + daoException.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean makeOrder(Account account) {
        addTransactions(account);
        try {
            DaoFactory factory = new MySqlDaoFactory();
            IGenDao dao = factory.getDao(factory.getConnection(), Account.class);
            boolean paid = account.getMoney(price);
            if (!paid) throw new Exception("There isn`t enough money on the card or false password");
            for (Transaction item : transactions) account.addTransaction(item);
            dao.update(account);
            status = "executed";
            dao = factory.getDao(factory.getConnection(), Order.class);
            dao.update(this);
            System.out.println("Transaction is completely");
            return true;
        } catch (Exception e) {
            System.out.println("Error with making order :" + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        String temp = "Order: " +
                "\nOrder id: " + id +
                "\nPrice: " + price +
                "\nStatus: " + status +
                "\nProducts in order: ";
        for (OrderLine orderLine: lines){
            temp+= orderLine.toString();
        }
        return temp;

    }
}
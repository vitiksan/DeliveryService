package com.DevStarters.Domain;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.Order.Order;
import com.DevStarters.Domain.Order.OrderLine;
import com.DevStarters.Domain.PaymentSystem.Account;
import com.DevStarters.MySql.MySqlDaoFactory;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class User implements Identificator<Integer> {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String address;
    private LocalDate bornDate;
    private ArrayList<Account> accounts;
    private HashSet<Order> orders;

    public User() {
        name = "none";
        surname = "none";
        login = "none";
        password = "1234";
        address = "none";
        bornDate = LocalDate.of(1900, 1, 5);
        accounts = new ArrayList<Account>();
        orders = new HashSet<>();
    }

    public User(String name, String surname, String login, String password,
                String address, int year, int month, int day) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.address = address;
        bornDate = LocalDate.of(year, month, day);
        accounts = new ArrayList<Account>();
        orders = new HashSet<>();
    }

    public User(String name, String surname, String login, String password,
                String address, int year, int month, int day, int passForAccount) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.address = address;
        bornDate = LocalDate.of(year, month, day);
        accounts = new ArrayList<Account>();
        accounts.add(createAccount(passForAccount));
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    protected void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Account createAccount(int pass) {
        DaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
            return (Account) dao.create(new Account(id, pass));
        } catch (DaoException e) {
            System.out.println("Error with create Account in Database :" + e.getMessage());
            return null;
        }
    }

    public void addAccount(Account account) {
        DaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
            Account getAccount = (Account) dao.create(account);
            if (getAccount == null) throw new DaoException();
        } catch (DaoException e) {
            System.out.println("Error with add Account to Database :" + e.getMessage());
        }
    }

    public void deleteAccount() {
        Scanner in = new Scanner(System.in);
        String card = choiceAccount().getCardNumber();
        DaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
            for (Account account : accounts) {
                if (account.getCardNumber().equals(card)) {
                    if (account.getBalance() == 0) {
                        dao.delete(account);
                        accounts.remove(account);
                    } else {
                        if (accounts.size() > 1 && !accounts.get(0).equals(account)) {
                            accounts.get(0).fillBalance(
                                    accounts.get(0).getBalance() + account.getBalance());
                            System.out.println("Many transfer from from card(" + account.getCardNumber() +
                                    ") to card(" + accounts.get(0).getCardNumber() + ")");
                            dao.delete(account);
                            accounts.remove(account);
                        } else System.out.println("You can`t delete this account, " +
                                "because you`ve got many on it");
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public Account choiceAccount() {
        Scanner in = new Scanner(System.in);
        boolean isAccount = false;
        Account temp = null;
        for (Order order : orders) System.out.println(order.toString());
        do {
            System.out.print("Enter id account: ");
            int accountId = in.nextInt();
            for (Account account : accounts) {
                if (account.getId() == accountId) {
                    temp = account;
                }
            }
            if (!isAccount) System.out.println("Not found this account");
        } while (!isAccount);
        return temp;
    }

    public HashSet<Order> getOrders() {
        return orders;
    }

    public void setOrders(HashSet<Order> orders) {
        this.orders = orders;
    }

    public void addNewOrder() {
        DaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
            orders.add((Order) dao.create(new Order(id)));
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    public Order choiceOder() {
        Scanner in = new Scanner(System.in);
        boolean isOrder = false;
        Order temp = null;
        if (orders.size() > 0) {
            for (Order order : orders) System.out.println(order.toString());
            do {
                System.out.print("Enter id order do you want to add product for: ");
                int orderId = in.nextInt();
                for (Order order : orders) {
                    if (order.getId() == orderId && !order.getStatus().equals("executed")) {
                        temp = order;
                    }
                }
                if (!isOrder) System.out.println("Not found this order or this order`s executed");
            } while (!isOrder);
        } else System.out.println("You have not orders");
        return temp;
    }

    public void addProductToOrder() {
        DaoFactory factory = new MySqlDaoFactory();
        Scanner in = new Scanner(System.in);
        int orderId = choiceOder().getId();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
            ArrayList<Product> products = dao.readAll();
            for (Product item : products) System.out.println(item.toString());
            System.out.print("Enter id of product which you want to buy: ");
            int id = in.nextInt();
            for (Product item : products) {
                if (item.getId() == id) {
                    System.out.print("Enter count of product: ");
                    int count = in.nextInt();
                    for (Order order : orders)
                        if (order.getId() == orderId && !order.getStatus().equals("executed")) {
                            order.addNewLine(new OrderLine(orderId, item, count));
                        }
                }
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeOrder() {
        Scanner in = new Scanner(System.in);
        int orderId = choiceOder().getId();
        Account accountForPayment = choiceAccount();
        for (Order order : orders) {
            if (order.getId() == orderId) order.makeOrder(accountForPayment);
        }
    }
}
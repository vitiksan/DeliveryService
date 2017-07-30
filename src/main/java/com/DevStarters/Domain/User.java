package com.DevStarters.Domain;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.PaymentSystem.Account;
import com.DevStarters.MySql.MySqlDaoFactory;

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

    public User() {
        name = "none";
        surname = "none";
        login = "none";
        password = "1234";
        address = "none";
        bornDate = LocalDate.of(1900, 1, 5);
        accounts = new ArrayList<Account>();
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
            return (Account) dao.create(new Account(id,pass));
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

    public void deleteAccount(){
        Scanner in = new Scanner(System.in);
        for (Account account: accounts){
            System.out.println(account.toString());
        }
        System.out.print("Enter account`s card number which you want to delete :");
        String card = in.nextLine();
        DaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(),Account.class);
            for (Account account: accounts){
                if (account.getCardNumber().equals(card)){
                    if (account.getBalance()==0){
                        dao.delete(account);
                        accounts.remove(account);
                    }else {
                        if (accounts.size()>1 && !accounts.get(0).equals(account)){
                            accounts.get(0).fillBalance(
                                    accounts.get(0).getBalance()+account.getBalance());
                            System.out.println("Many transfer from from card("+account.getCardNumber()+
                            ") to card("+accounts.get(0).getCardNumber()+")");
                            dao.delete(account);
                            accounts.remove(account);
                        }else System.out.println("You can`t delete this account, " +
                                "because you`ve got many on it");
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
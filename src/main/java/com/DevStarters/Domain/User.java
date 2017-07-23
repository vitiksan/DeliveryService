package com.DevStarters.Domain;

import com.DevStarters.Domain.PaymentSystem.Account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
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
        login = "root";
        password = "1234";
        address = "none";
        bornDate = LocalDate.of(1900, 1, 5);
        accounts = new ArrayList<>();
    }

    public User(String name, String surname, String login, String password,
                String address, int year, int month, int day, ArrayList<Account> accounts) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.address = address;
        bornDate = LocalDate.of(year, month, day);
        accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public void addAccount() {
        accounts.add(new Account());
    }

    public void addAccount(double balance, int pass) {
        accounts.add(new Account(this.id, balance, pass));
    }

    public Account deleteAccount() {
        Scanner in = new Scanner(System.in);
        Account temp = null;
        int id;
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        System.out.println("Enter id of account which you want to delete: ");
        id = Integer.parseInt(in.next());
        for (Account account : accounts) {
            if (id == account.getId()) {
                temp = account;
                accounts.remove(account);
            }
        }
        return temp;
    }
}

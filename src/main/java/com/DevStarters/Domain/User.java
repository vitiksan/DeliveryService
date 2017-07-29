package com.DevStarters.Domain;

import com.DevStarters.DAO.Identificator;
import com.DevStarters.Domain.PaymentSystem.Account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Identificator<Integer> {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String address;
    private LocalDate bornDate;
    private Account account;

    public User() {
        name = "none";
        surname = "none";
        login = "root";
        password = "1234";
        address = "none";
        bornDate = LocalDate.of(1900, 1, 5);
        account = new Account();
    }

    public User(String name, String surname, String login, String password,
                String address, int year, int month, int day) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.address = address;
        bornDate = LocalDate.of(year, month, day);
        account = new Account();
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
}
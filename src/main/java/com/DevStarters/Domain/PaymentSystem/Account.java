package com.DevStarters.Domain.PaymentSystem;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Account {
    private int id;
    private String cardNumber;
    private double balance;
    private int pass;
    private LocalDate expirationCardDate;
    private int userId;
    private HashSet<Transaction> transactions = new HashSet<Transaction>();

    public Account() {
        this.cardNumber = generateVCNumber();
    }

    public Account(int userId) {
        this.userId = userId;
        this.cardNumber = generateVCNumber();
        balance = 0;
        pass = 0000;
    }

    public Account(int userId,double balance, int pass) {
        this.cardNumber = generateVCNumber();
        this.balance = balance;
        this.pass = pass;
        this.userId = userId;
        setExpCard();
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    protected void setCardNumber(String numberAccount) {
        this.cardNumber = numberAccount;
    }

    /**
     * @return - генерація 16-значного номера рахунку
     */
    private String generateVCNumber() {
        String temp = "4";
        Date currentDate = new Date();
        Random random = new Random(currentDate.getTime());
        for (int i = 0; i < 15; i++) {
            temp += String.valueOf(random.nextInt(10));
        }
        return temp;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Зняття грошей з рахунку
     *
     * @param count - Сума, яку потрібно зняти з рахунку
     * @return - true якщо операція виконана, false якщо ні
     */
    public boolean getMoney(double count) {
        boolean temp = checkPassword();
        if (count <= this.balance && temp) {
            this.balance -= count;
            return true;
        }
        return false;
    }

    /**
     * Поповнення рахунку
     *
     * @param count - Сума, на яку поповнюється рахунок
     * @return - true якщо операція виконана, false якщо ні
     */
    public boolean fillBalance(double count) {
        if (count > 0) {
            balance += count;
            return true;
        }
        return false;
    }

    public int getPass() {
        return pass;
    }

    protected void setPass(int pass) {
        this.pass = pass;
    }

    public boolean checkPassword() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter password: ");
        return (in.nextInt() == pass);
    }


    public void changePassword() throws Exception {
        Scanner in = new Scanner(System.in);
        boolean temp;
        int newPass, repeatNewPass;
            temp = checkPassword();
        if (temp) {
            do {
                System.out.println("Enter new password: ");
                newPass = in.nextInt();
                System.out.println("Repeat new password: ");
                repeatNewPass = in.nextInt();
            } while (newPass != repeatNewPass);
        }
    }

    private void setExpCard() {
        expirationCardDate = LocalDate.now().plus(Period.ofYears(4));
    }

    public LocalDate getExpirationCardDate() {
        return expirationCardDate;
    }

    protected void setExpirationCardDate(LocalDate expirationCardDate) {
        this.expirationCardDate = expirationCardDate;
    }

    public boolean checkExpDate() {
        return expirationCardDate.isBefore(LocalDate.now().minus(Period.ofDays(7)));
    }

    public int getUserId() {
        return userId;
    }

    protected void setUserId(int userId) {
        this.userId = userId;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}

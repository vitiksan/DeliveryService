package com.DevStarters.Domain.PaymentSystem;

import com.DevStarters.DAO.Identificator;
import com.DevStarters.Util.CardNumberGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Scanner;

public class Account implements Identificator<Integer> {
    private int id;
    private String cardNumber;
    private double balance;
    private int pass;
    private int userId;
    private LocalDate expirationCardDate;
    private HashSet<Transaction> transactions = new HashSet<Transaction>();

    public Account() {
        this.cardNumber = CardNumberGenerator.generateVCNumber();
        balance = 0;
        pass = 0000;
        userId = 0;
        setExpCard();
    }

    public Account(int userId, int pass) {
        cardNumber = CardNumberGenerator.generateVCNumber();
        balance = 0;
        this.userId = userId;
        this.pass = pass;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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

    public boolean getMoney(double money,int pass) {
        boolean temp = checkPassword();
        if (money <= this.balance && temp) {
            this.balance -= money;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public HashSet<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(HashSet<Transaction> transactions) {
        this.transactions = transactions;
    }
}

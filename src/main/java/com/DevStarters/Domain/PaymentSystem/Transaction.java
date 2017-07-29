package com.DevStarters.Domain.PaymentSystem;

import com.DevStarters.DAO.Identificator;

import java.time.LocalDateTime;

public class Transaction implements Identificator<Integer> {
    private int id;
    private int senderAccountId;
    private String recipientCard;
    private double amount;
    private LocalDateTime transactionTime;
    private int orderId;

    public Transaction() {
        senderAccountId = 0;
        recipientCard = "";
        amount = 0;
    }

    public Transaction(int senderAccountId, String recipientCard, double amount) {
        this.senderAccountId = senderAccountId;
        this.recipientCard = recipientCard;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getRecipientCard() {
        return recipientCard;
    }

    public void setRecipientCard(String recipientCard) {
        this.recipientCard = recipientCard;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

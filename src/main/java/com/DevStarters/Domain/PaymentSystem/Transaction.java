package com.DevStarters.Domain.PaymentSystem;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int accountIdFrom;
    private int accountIdTo;
    private LocalDateTime transactionTime;

    public Transaction() {
        accountIdFrom=0;
        accountIdTo=0;
    }

    public Transaction(int accountIdFrom, int accountIdTo) {
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public int getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(int accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public int getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(int accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}

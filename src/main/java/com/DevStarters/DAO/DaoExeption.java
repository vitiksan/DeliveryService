package com.DevStarters.DAO;

public class DaoExeption extends Exception{

    public DaoExeption() {
    }

    public DaoExeption(String message){
        super(message);
    }

    public DaoExeption(String message, Throwable cause){
        super(message,cause);
    }

    public DaoExeption(Throwable cause){
        super(cause);
    }

    protected DaoExeption(String message, Throwable cause,
                          boolean enableSuppression, boolean writableTrace){
        super(message,cause,enableSuppression,writableTrace);
    }
}

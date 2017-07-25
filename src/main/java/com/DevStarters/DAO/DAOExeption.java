package com.DevStarters.DAO;

public class DAOExeption extends Exception{

    public DAOExeption() {
    }

    public DAOExeption(String message){
        super(message);
    }

    public DAOExeption(String message,Throwable cause){
        super(message,cause);
    }

    public DAOExeption(Throwable cause){
        super(cause);
    }

    protected DAOExeption(String message,Throwable cause,
                          boolean enableSuppression,boolean writableTrace){
        super(message,cause,enableSuppression,writableTrace);
    }
}

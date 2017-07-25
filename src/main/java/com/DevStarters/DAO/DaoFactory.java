package com.DevStarters.DAO;

public interface DaoFactory<T> {

    public interface DAOCreator<T>{
        public AbstractDao create(T connection);
    }

    public T getConnection()throws DaoExeption;

    public AbstractDao getDao(T connection,Class daoClass)throws DaoExeption;
}

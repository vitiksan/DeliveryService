package com.DevStarters.DAO;

import java.io.Serializable;
import java.util.ArrayList;

public interface IGenDao <T, PK extends Serializable> {

    T create(T obj) throws DAOExeption;

    T read(int key) throws DAOExeption;

    ArrayList<T> readAll() throws DAOExeption;

    boolean update(T obj) throws DAOExeption;

    boolean delete(T obj) throws DAOExeption;
}

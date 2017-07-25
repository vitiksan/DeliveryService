package com.DevStarters.DAO;

import java.io.Serializable;
import java.util.ArrayList;

public interface IGenDao <T, PK extends Serializable> {

    T create(T obj) throws DaoExeption;

    T read(int key) throws DaoExeption;

    ArrayList<T> readAll() throws DaoExeption;

    boolean update(T obj) throws DaoExeption;

    boolean delete(T obj) throws DaoExeption;
}

package com.DevStarters.DAO;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class AbstractDao<T extends Identificator<PK>, PK extends Serializable> implements IGenDao<T,PK> {
    private Connection connection;
    private static final Logger log = Logger.getLogger(AbstractDao.class);
    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();

    public abstract String getSelectAllQuery();

    public abstract String getUpdateQuery();

    public abstract String getCreateQuery();

    public abstract String getDeleteQuery();

    public abstract ArrayList<T> parsData(ResultSet rs) throws DaoExeption;

    public abstract void parsUpdate(PreparedStatement prSt, T obj) throws DaoExeption;

    public abstract void parsInsert(PreparedStatement prSt, T obj) throws DaoExeption;

    @Override
    public T create(T obj) throws DaoExeption {
        T temp;
        String query = getCreateQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            parsInsert(prSt, obj);
            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoExeption("Error. Created more then 1 object " + count);
        } catch (Exception e) {
            log.error("Error with create object"+e.getMessage());
            throw new DaoExeption(e);
        }


        query = getSelectQuery() + "(SELECT last_insert_id());";

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            ResultSet rs = prSt.executeQuery();
            ArrayList<T> someList = parsData(rs);

            if (someList == null || someList.size() !=1)
                throw new DaoExeption("Error with search created object by id");
            temp = someList.iterator().next();
        } catch (Exception e) {
            log.error("Error with search object"+e.getMessage());
            throw new DaoExeption(e);
        }
        return temp;
    }

    @Override
    public T read(int id) throws DaoExeption {
        ArrayList<T> someList;
        String query = getSelectQuery() + "?;";

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            prSt.setInt(1, id);
            ResultSet rs = prSt.executeQuery();
            someList = parsData(rs);
        } catch (Exception e) {
            log.error("Error with read object"+e.getMessage());
            throw new DaoExeption(e);
        }

        if (someList == null || someList.size() == 0) return null;

        if (someList.size() > 1) {
            throw new DaoExeption("Отримано забато даних");
        }

        return someList.iterator().next();
    }

    @Override
    public ArrayList<T> readAll() throws DaoExeption {
        ArrayList<T> someList;
        String query = getSelectAllQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            ResultSet resultSet = prSt.executeQuery();
            someList = parsData(resultSet);
        } catch (Exception e) {
            log.error("Error with read all object"+e.getMessage());
            throw new DaoExeption(e);
        }
        return someList;
    }

    @Override
    public boolean update(T obj) throws DaoExeption {
        String query = getUpdateQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            parsUpdate(prSt, obj);
            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoExeption("Error. Modified more then 1 field " + count);
            else return true;
        } catch (Exception e) {
            log.error("Error with update object"+e.getMessage());
            throw new DaoExeption(e);
        }
    }

    @Override
    public boolean delete(T obj) throws DaoExeption {
        String query = getDeleteQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            try {
                prSt.setObject(1, obj.getId());
            } catch (Exception e) {
                throw new DaoExeption(e);
            }

            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoExeption("Error. Deleted more then 1 field " + count);
            else return true;
        } catch (Exception e) {
            log.error("Error with delete object"+e.getMessage());
            throw new DaoExeption(e);
        }
    }
}
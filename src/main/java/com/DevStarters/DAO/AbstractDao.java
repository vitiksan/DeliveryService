package com.DevStarters.DAO;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class AbstractDao<T extends Identificator<PK>, PK extends Serializable> implements IGenDao<T, PK> {
    private Connection connection;
    private static final Logger log = Logger.getLogger(AbstractDao.class);

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();

    public abstract String getSelectQueryWithoutJoin();

    public abstract String getSelectAllQuery();

    public abstract String getUpdateQuery();

    public abstract String getCreateQuery();

    public abstract String getDeleteQuery();

    public abstract ArrayList<T> parsData(ResultSet rs) throws DaoException;

    public abstract void parsUpdate(PreparedStatement prSt, T obj) throws DaoException;

    public abstract void parsInsert(PreparedStatement prSt, T obj) throws DaoException;

    @Override
    public T create(T obj) throws DaoException {
        T temp;
        String query = getCreateQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            parsInsert(prSt, obj);
            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoException("Error. Created more then 1 object " + count);
        } catch (Exception e) {
            log.error("Error with create object" + e.getMessage());
            throw new DaoException(e);
        }


        query = getSelectQuery() + "(SELECT last_insert_id());";

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            ResultSet rs = prSt.executeQuery();
            ArrayList<T> someList = parsData(rs);

            if (someList == null || someList.size() != 1) {
                query = getSelectQueryWithoutJoin() + "(SELECT last_insert_id());";
                rs = prSt.executeQuery();
                someList = parsData(rs);
                if (someList == null || someList.size() != 1)
                    throw new DaoException("Error with search created object by id");
            }
            temp = someList.iterator().next();
        } catch (Exception e) {
            log.error("Error with search object" + e.getMessage());
            throw new DaoException(e);
        }
        return temp;
    }

    @Override
    public T read(int id) throws DaoException {
        ArrayList<T> someList;
        String query = getSelectQuery() + "?;";

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            prSt.setInt(1, id);
            ResultSet rs = prSt.executeQuery();
            someList = parsData(rs);

            if (someList == null || someList.size() == 0) {
                query = getSelectQueryWithoutJoin() + "?;";
                prSt.setInt(1, id);
                rs = prSt.executeQuery();
                someList = parsData(rs);
                if (someList == null || someList.size() == 0) {
                    return null;
                }
            }

        } catch (Exception e) {
            log.error("Error with read object" + e.getMessage());
            throw new DaoException(e);
        }

        if (someList.size() > 1) {
            throw new DaoException("Отримано забато даних");
        }

        return someList.iterator().next();
    }

    @Override
    public ArrayList<T> readAll() throws DaoException {
        ArrayList<T> someList;
        String query = getSelectAllQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            ResultSet resultSet = prSt.executeQuery();
            someList = parsData(resultSet);
        } catch (Exception e) {
            log.error("Error with read all object" + e.getMessage());
            throw new DaoException(e);
        }
        return someList;
    }

    @Override
    public boolean update(T obj) throws DaoException {
        String query = getUpdateQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            parsUpdate(prSt, obj);
            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoException("Error. Modified more then 1 field " + count);
            else return true;
        } catch (Exception e) {
            log.error("Error with update object" + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(T obj) throws DaoException {
        String query = getDeleteQuery();

        try (PreparedStatement prSt = connection.prepareStatement(query)) {
            try {
                prSt.setObject(1, obj.getId());
            } catch (Exception e) {
                throw new DaoException(e);
            }

            int count = prSt.executeUpdate();
            if (count != 1) throw new DaoException("Error. Deleted more then 1 field " + count);
            else return true;
        } catch (Exception e) {
            log.error("Error with delete object" + e.getMessage());
            throw new DaoException(e);
        }
    }
}
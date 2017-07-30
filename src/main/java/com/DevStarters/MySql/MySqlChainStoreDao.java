package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.ChainStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlChainStoreDao extends AbstractDao<ChainStore,Integer>{
    public MySqlChainStoreDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM chain_stores WHERE chain_store_id=";
    }

    @Override
    public String getSelectQueryWithoutJoin() {
        return "SELECT * FROM chain_stores WHERE chain_store_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM chain_stores;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE chain_stores SET chain_store_name=?,chain_store_description=?,chain_store_address=?," +
                "chain_store_kitchen=?,chain_store_type=?,card_for_payments=? WHERE chain_store_id=?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO chain_stores(chain_store_name,chain_store_description,chain_store_address," +
                "chain_store_kitchen,chain_store_type,card_for_payments) VALUES (?,?,?,?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM chain_stores WHERE chain_store_id=?;";
    }

    @Override
    public ArrayList<ChainStore> parsData(ResultSet rs,boolean isJoin) throws DaoException {
        ArrayList<ChainStore> chainStores = new ArrayList<ChainStore>();
        try {
            while (rs.next()) {
               ChainStore chainStore=new ChainStore();
               chainStore.setId(rs.getInt("chain_store_id"));
               chainStore.setName(rs.getString("chain_store_name"));
               chainStore.setDescription(rs.getString("chain_store_description"));
               chainStore.setAddress(rs.getString("chain_store_address"));
               chainStore.setKitchen(rs.getString("chain_store_kitchen"));
               chainStore.setType(rs.getString("chain_store_type"));
               chainStore.setCardForPayments(rs.getString("card_for_payments"));
               chainStores.add(chainStore);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return chainStores;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, ChainStore obj) throws DaoException {
        try {
          prSt.setString(1,obj.getName());
          prSt.setString(2,obj.getDescription());
          prSt.setString(3,obj.getAddress());
          prSt.setString(4,obj.getKitchen());
          prSt.setString(5,obj.getType());
          prSt.setString(6,obj.getCardForPayments());
          prSt.setInt(7,obj.getId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, ChainStore obj) throws DaoException {
        try {
            prSt.setString(1,obj.getName());
            prSt.setString(2,obj.getDescription());
            prSt.setString(3,obj.getAddress());
            prSt.setString(4,obj.getKitchen());
            prSt.setString(5,obj.getType());
            prSt.setString(6,obj.getCardForPayments());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}

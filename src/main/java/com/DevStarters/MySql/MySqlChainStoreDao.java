package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MySqlChainStoreDao extends AbstractDao<ChainStore, Integer> {
    public MySqlChainStoreDao(Connection connection) {
        super(connection);
    }

    private class ExtendsProduct extends Product {
        public ExtendsProduct() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM chain_stores chs LEFT JOIN products p ON(chs.chain_store_id=p.vendor_id) WHERE chain_store_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM chain_stores chs LEFT JOIN products p ON(chs.chain_store_id=p.vendor_id);";
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
    public ArrayList<ChainStore> parsData(ResultSet rs) throws DaoException {
        ArrayList<ChainStore> chainStores = new ArrayList<ChainStore>();
        try {
            while (rs.next()) {
                ChainStore chainStore = new ChainStore();
                ExtendsProduct product = new ExtendsProduct();
                boolean isStore = false;

                if (rs.getDate("production_date") != null) {
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("product_price"));
                    product.setDescription(rs.getString("product_description"));
                    product.setVendorId(rs.getInt("vendor_id"));
                    product.setProductionDate(rs.getDate("production_date").toLocalDate());
                    product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                }

                chainStore.setId(rs.getInt("chain_store_id"));
                chainStore.setName(rs.getString("chain_store_name"));
                chainStore.setDescription(rs.getString("chain_store_description"));
                chainStore.setAddress(rs.getString("chain_store_address"));
                chainStore.setKitchen(rs.getString("chain_store_kitchen"));
                chainStore.setType(rs.getString("chain_store_type"));
                chainStore.setCardForPayments(rs.getString("card_for_payments"));

                if (product.getId() != 0) {
                    for (ChainStore store : chainStores) {
                        if (store.getId() == chainStore.getId()) {
                            store.addItem(product);
                            isStore = true;
                        }
                    }
                    if (!isStore) {
                        chainStore.addItem(product);
                        chainStores.add(chainStore);
                    }
                } else chainStores.add(chainStore);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return chainStores;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, ChainStore obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getDescription());
            prSt.setString(3, obj.getAddress());
            prSt.setString(4, obj.getKitchen());
            prSt.setString(5, obj.getType());
            prSt.setString(6, obj.getCardForPayments());
            prSt.setInt(7, obj.getId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, ChainStore obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getDescription());
            prSt.setString(3, obj.getAddress());
            prSt.setString(4, obj.getKitchen());
            prSt.setString(5, obj.getType());
            prSt.setString(6, obj.getCardForPayments());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public ChainStore createWithField(int fKey) throws DaoException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of store: ");
        String name = in.nextLine();
        System.out.print("Enter description of store: ");
        String description = in.nextLine();
        System.out.print("Enter address of store: ");
        String address = in.nextLine();
        System.out.print("Enter kitchen of store: ");
        String kitchen = in.nextLine();
        System.out.println("Enter type of store: ");
        String type = in.nextLine();
        ChainStore tempChain = new ChainStore(name, description, address, kitchen, type);
        return create(tempChain);
    }
}
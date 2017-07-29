package com.DevStarters.MySql;// Created by on 28.07.2017.

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySqlProductDao extends AbstractDao<Product, Integer> {

    public MySqlProductDao(Connection connection) {
        super(connection);
    }

    private class ExtendProduct extends Product {
        public ExtendProduct() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM products WHERE order_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM products;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE products SET product_name=?,product_price=?,product_description=?," +
                "vendor_id=?,production_date=?,expiration_date=? WHERE product_id=?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO products (NULL,?,?,?,?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM products WHERE product_id=?;";
    }

    @Override
    public ArrayList<Product> parsData(ResultSet rs) throws DaoException {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            while (rs.next()) {
                ExtendProduct product = new ExtendProduct();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("product_price"));
                product.setDescription(rs.getString("product_description"));
                product.setVendorId(rs.getInt("vendor_id"));
                product.setProductionDate(rs.getDate("production_date").toLocalDate());
                product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                products.add(product);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return products;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, Product obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setDouble(2, obj.getPrice());
            prSt.setString(3, obj.getDescription());
            prSt.setInt(4, obj.getVendorId());
            prSt.setDate(5, java.sql.Date.valueOf(obj.getProductionDate()));
            prSt.setDate(6, java.sql.Date.valueOf(obj.getExpirationDate()));
            prSt.setInt(7, obj.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, Product obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setDouble(2, obj.getPrice());
            prSt.setString(3, obj.getDescription());
            prSt.setInt(4, obj.getVendorId());
            prSt.setDate(5, java.sql.Date.valueOf(obj.getProductionDate()));
            prSt.setDate(6, java.sql.Date.valueOf(obj.getExpirationDate()));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}

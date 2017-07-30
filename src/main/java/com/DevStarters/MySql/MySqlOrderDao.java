package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.Order.Order;
import com.DevStarters.Domain.Order.OrderLine;
import com.DevStarters.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlOrderDao extends AbstractDao<Order, Integer> {

    public MySqlOrderDao(Connection connection) {
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

    private class ExtendOrderLine extends OrderLine {
        public ExtendOrderLine() {
            super();
        }

    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM orders o JOIN order_lines ol USING(order_id) JOIN products pr " +
                "USING(product_id) WHERE order_id=";
    }

    @Override
    public String getSelectQueryWithoutJoin() {
        return "SELECT * FROM orders WHERE order_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM orders o JOIN order_lines ol USING(order_id) JOIN products pr " +
                "USING(product_id);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE orders SET user_id=?,order_price=?,order_status=? WHERE order_id=?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO orders (user_id,order_price,order_status) VALUES(?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM orders WHERE order_id=?;";
    }

    @Override
    public ArrayList<Order> parsData(ResultSet rs, boolean isJoin) throws DaoException {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            while (rs.next()) {
                Order order = new Order();
                ExtendProduct product = new ExtendProduct();
                ExtendOrderLine line = new ExtendOrderLine();

                order.setUserId(rs.getInt("user_id"));
                order.setPrice(rs.getDouble("order_price"));
                order.setStatus(rs.getString("order_status"));
                order.setId(rs.getInt("order_id"));

                if (isJoin) {
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("product_price"));
                    product.setDescription(rs.getString("product_description"));
                    product.setVendorId(rs.getInt("vendor_id"));
                    product.setProductionDate(rs.getDate("production_date").toLocalDate());
                    product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());

                    line.setCount(rs.getInt("product_count"));
                    line.setId(rs.getInt("order_line_id"));
                    line.setProduct(product);
                    line.setPrice(rs.getDouble("order_line_price"));
                    line.setOrderId(rs.getInt("order_id"));

                    boolean temp = false;
                    for (Order order1 : orders) {

                        if (order1.getId() == order.getId()) {
                            order1.addNewLine(line);
                            temp = true;
                        }
                    }
                    if (!temp) {
                        order.addNewLine(line);
                        orders.add(order);
                    }
                }else
                    orders.add(order);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, Order obj) throws DaoException {
        try {
            prSt.setInt(1, obj.getUserId());
            prSt.setDouble(2, obj.getPrice());
            prSt.setString(3, obj.getStatus());
            prSt.setInt(4, obj.getId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, Order obj) throws DaoException {
        try {
            prSt.setInt(1, obj.getUserId());
            prSt.setDouble(2, obj.getPrice());
            prSt.setString(3, obj.getStatus());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}

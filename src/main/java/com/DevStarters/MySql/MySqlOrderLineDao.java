package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.Order.OrderLine;
import com.DevStarters.Domain.Product;
import org.apache.commons.lang.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MySqlOrderLineDao extends AbstractDao<OrderLine, Integer> {

    private class ExtendProduct extends Product {
        public ExtendProduct() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    public MySqlOrderLineDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM order_lines ol LEFT JOIN products p USING(product_id) WHERE order_line_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM order_lines ol LEFT JOIN products p USING(product_id);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE order_lines SET product_id=?,product_count=?,order_line_price=? " +
                "WHERE order_line_id=?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO order_lines(product_id,product_count,order_line_price," +
                "order_id) VALUES(?,?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM order_lines WHERE order_line_id=?;";
    }

    @Override
    public ArrayList<OrderLine> parsData(ResultSet rs) throws DaoException {
        ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
        try {
            while (rs.next()) {
                OrderLine line = new OrderLine();
                ExtendProduct product = new ExtendProduct();

                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("product_price"));
                product.setDescription(rs.getString("product_description"));
                product.setVendorId(rs.getInt("vendor_id"));
                product.setProductionDate(rs.getDate("production_date").toLocalDate());
                product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());

                line.setCount(rs.getInt("product_count"));
                line.setId(rs.getInt("order_line_id"));
                line.setPrice(rs.getDouble("order_line_price"));
                line.setOrderId(rs.getInt("order_id"));
                line.setProduct(product);

                lines.add(line);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return lines;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, OrderLine obj) throws DaoException {
        try {
            prSt.setInt(1, obj.getProduct().getId());
            prSt.setInt(2, obj.getCount());
            prSt.setDouble(3, obj.getPrice());
            prSt.setInt(4, obj.getId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, OrderLine obj) throws DaoException {
        try {
            prSt.setInt(1, obj.getProduct().getId());
            prSt.setInt(2, obj.getCount());
            prSt.setDouble(3, obj.getPrice());
            prSt.setInt(4, obj.getOrderId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public OrderLine createWithField(int fKey) throws DaoException {
        throw new NotImplementedException();
    }
}

package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.Order.OrderLine;
import com.DevStarters.Domain.Product;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlOrderLineDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
        AbstractDao daoProduct = factory.getDao(factory.getConnection(), Product.class);
        OrderLine orderLine=new OrderLine();
        orderLine.setProduct((Product) daoProduct.read(1));
        orderLine.setCount(2);
        orderLine.setPrice(36);
        orderLine.setOrderId(1);
        OrderLine getOrderLine = (OrderLine) dao.create(orderLine);
        assertNotNull(getOrderLine);
        System.out.println(getOrderLine.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
        OrderLine findOrderLine = (OrderLine) dao.read(1);
        assertNotNull(findOrderLine);
        System.out.println(findOrderLine.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
        ArrayList<OrderLine> orderLine = dao.readAll();
        assertNotNull(orderLine);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
        OrderLine orderLine = (OrderLine) dao.read(1);
        orderLine.setCount(3);
        orderLine.setPrice(54);
        dao.update(orderLine);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), OrderLine.class);
        OrderLine orderLine = (OrderLine) dao.read(1);
        dao.delete(orderLine);
    }
}
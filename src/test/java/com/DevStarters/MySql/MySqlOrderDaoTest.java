package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.Order.Order;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlOrderDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
        Order order = new Order();
        order.setUserId(1);
        order.setPrice(18);
        order.setStatus("not executed");
        Order getorder = (Order) dao.create(order);
        assertNotNull(getorder);
        System.out.println(getorder.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
        Order findOrder = (Order) dao.read(5);
        assertNotNull(findOrder);
        System.out.println(findOrder.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
        ArrayList<Order> order = dao.readAll();
        assertNotNull(order);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
        Order orders = (Order) dao.read(5);
        orders.setStatus("waiting");
        dao.update(orders);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Order.class);
        Order order = (Order) dao.read(10);
        dao.delete(order);
    }

}
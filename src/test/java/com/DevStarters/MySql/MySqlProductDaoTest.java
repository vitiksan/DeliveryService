package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlProductDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
        Product product=new Product();
        product.setName("Milk");
        product.setPrice(16);
        product.setDescription("без ГМО");
        product.setVendorId(2);
        product.setProductionDate(LocalDate.of(2017,07,25));
        product.setExpirationDate(LocalDate.of(2017,07,31));
        Product getproduct = (Product) dao.create(product);
        assertNotNull(getproduct);
        System.out.println(getproduct.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
        Product findproduct = (Product) dao.read(1);
        assertNotNull(findproduct);
        System.out.println(findproduct.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
        ArrayList<Product> products= dao.readAll();
        assertNotNull(products);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
        Product product = (Product) dao.read(1);
        product.setPrice(18);
        dao.update(product);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
        Product product = (Product) dao.read(1);
        dao.delete(product);
    }

}
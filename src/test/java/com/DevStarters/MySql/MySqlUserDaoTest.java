package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlUserDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        User user=new User();
        user.setName("Vitalik");
        user.setSurname("Mah");
        user.setAddress("Symonenka 5a");
        User getUser = (User) dao.create(user);
        assertNotNull(getUser);
        System.out.println(getUser.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        User findUser = (User) dao.read(1);
        assertNotNull(findUser);
        System.out.println(findUser.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        ArrayList<User> users = dao.readAll();
        assertNotNull(users);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        User user = (User) dao.read(1);
        user.setBornDate(LocalDate.of(1997,11,29));
        dao.update(user);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        User user = (User) dao.read(1);
        dao.delete(user);
    }
}
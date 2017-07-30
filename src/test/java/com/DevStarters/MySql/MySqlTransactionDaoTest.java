package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class MySqlTransactionDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Transaction.class);
        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(1);
        transaction.setAmount(2548.6);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setOrderId(1);
        Transaction getTransaction = (Transaction) dao.create(transaction);
        assertNotNull(getTransaction);
        System.out.println(getTransaction.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Transaction.class);
        Transaction findTransaction = (Transaction) dao.read(1);
        assertNotNull(findTransaction);
        System.out.println(findTransaction.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Transaction.class);
        ArrayList<Transaction> transactions = dao.readAll();
        assertNotNull(transactions);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Transaction.class);
        Transaction transaction = (Transaction) dao.read(1);
        transaction.setAmount(2547.8);
        dao.update(transaction);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Transaction.class);
        Transaction transaction = (Transaction) dao.read(1);
        dao.delete(transaction);
    }

}
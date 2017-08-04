package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.PaymentSystem.Account;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlAccountDaoTest {
    private class ExtendAccount extends Account {
        public ExtendAccount() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }

        @Override
        protected void setCardNumber(String numberAccount) {
            super.setCardNumber(numberAccount);
        }

        @Override
        protected void setPass(int pass) {
            super.setPass(pass);
        }

        @Override
        protected void setExpirationCardDate(LocalDate expirationCardDate) {
            super.setExpirationCardDate(expirationCardDate);
        }
    }

    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
        Account account = new Account();
        account.setUserId(2);
        Account getAccount = (Account) dao.create(account);
        assertNotNull(getAccount);
        System.out.println(getAccount.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
        Account findAccount = (Account) dao.read(2);
        assertNotNull(findAccount);
        System.out.println(findAccount.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
        ArrayList<Account> accounts = dao.readAll();
        assertNotNull(accounts);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
        Account account = (Account) dao.read(2);
        account.setBalance(256.3);
        dao.update(account);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), Account.class);
        Account account = (Account) dao.read(2);
        dao.delete(account);
    }
}
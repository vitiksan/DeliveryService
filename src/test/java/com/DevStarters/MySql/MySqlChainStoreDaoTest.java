package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.Domain.ChainStore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MySqlChainStoreDaoTest {
    @Test
    public void create() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
        ChainStore chainStore=new ChainStore();
        chainStore.setName("Япона Хата");
        chainStore.setDescription("yaponahata.com");
        chainStore.setAddress("вул. Незалежності, 16");
        chainStore.setKitchen("japanese");
        chainStore.setType("Суші-бар");
        chainStore.setCardForPayments("1234756890486235");
        ChainStore getchain = (ChainStore) dao.create(chainStore);
        assertNotNull(getchain);
        System.out.println(getchain.toString());
    }

    @Test
    public void read() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
        ChainStore findChain = (ChainStore) dao.read(1);
        assertNotNull(findChain);
        System.out.println(findChain.toString());
    }

    @Test
    public void readAll() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
        ArrayList<ChainStore> chainStore= dao.readAll();
        assertNotNull(chainStore);
    }

    @Test
    public void update() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
        ChainStore chainStore = (ChainStore) dao.read(1);
        chainStore.setDescription("Приймаємо замовлення з 09:30 до 23:00.");
        dao.update(chainStore);
    }

    @Test
    public void delete() throws Exception {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
        ChainStore chainStore = (ChainStore) dao.read(1);
        dao.delete(chainStore);
    }
}
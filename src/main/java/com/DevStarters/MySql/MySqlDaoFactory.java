package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoExeption;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.Order.Order;
import com.DevStarters.Domain.OrderLine;
import com.DevStarters.Domain.PaymentSystem.Account;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import com.DevStarters.Domain.Product;
import com.DevStarters.Domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DaoFactory<Connection> {

    private static final Logger log = Logger.getLogger(MySqlDaoFactory.class);
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://servlab.mysql.ukraine.com.ua/servlab_mahola?useSSL=false";
    private static String USERNAME = "servlab_mahola";
    private static String PASSWORD = "fp2dazf8";
    private Map<Class, DaoCreator> allDao;

    @Override
    public Connection getConnection() throws DaoExeption {
        Connection connection = null;
        try {
            Class.forName(driverName); // Завантажуємо клас драйвера
        } catch (ClassNotFoundException e) {
            log.error("Driver JDBC has NOT get");
            log.error(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("The successful connection for DB");
        } catch (SQLException e) {
            log.error("Failed connection for DB");
            log.error(e.getMessage());
        }
        return connection;
    }

    @Override
    public AbstractDao getDao(Connection connection, Class daoClass) throws DaoExeption {
        DaoCreator creator = allDao.get(daoClass);
        if (creator == null) {
            throw new DaoExeption("DAO for class " + daoClass + " not found");
        }
        return creator.create(connection);
    }


    public MySqlDaoFactory() {
        allDao = new HashMap<Class, DaoCreator>();

        allDao.put(User.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });
        allDao.put(ChainStore.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlChainStoreDao(connection);
            }
        });
        allDao.put(Order.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlOrderDao(connection);
            }
        });
        allDao.put(OrderLine.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlOrderLineDao(connection);
            }
        });
        allDao.put(Product.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlProductDao(connection);
            }
        });
        allDao.put(Transaction.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlTransactionDao(connection);
            }
        });
        allDao.put(Account.class, new DaoCreator<Connection>() {
            @Override
            public AbstractDao create(Connection connection) {
                return new MySqlAccountDao(connection);
            }
        });
    }
}

package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.PaymentSystem.Account;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MySqlAccountDao extends AbstractDao<Account, Integer> {

    private static final Logger log = Logger.getLogger(MySqlDaoFactory.class);

    public MySqlAccountDao(Connection connection) {
        super(connection);
    }


    private class AccountForDB extends Account {
        public AccountForDB() throws DaoException {
        }

        public void setId(int id) {
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

    private class TransactionForDB extends Transaction {
        public TransactionForDB() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM accounts ac LEFT JOIN transactions tr " +
                "ON (tr.sender_account_id=ac.account_id) WHERE account_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM accounts ac LEFT JOIN transactions tr " +
                "ON (tr.sender_account_id=ac.account_id)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE accounts SET account_card_number=?,account_balance=?," +
                "account_pass=? WHERE account_id=?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO accounts (account_card_number,account_balance," +
                "account_pass,account_expiration_date_card, user_id) VALUES(?,?,?,?,?)";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM accounts WHERE account_id=?";
    }

    @Override
    public ArrayList<Account> parsData(ResultSet rs) throws DaoException {
        ArrayList<Account> accounts = new ArrayList<Account>();

        try {
            while (rs.next()) {
                AccountForDB account = new AccountForDB();
                TransactionForDB transaction = new TransactionForDB();
                boolean isAccount = false;

                if (rs.getTimestamp("transaction_time") != null) {
                    transaction.setId(rs.getInt("transaction_id"));
                    transaction.setSenderAccountId(rs.getInt("sender_account_id"));
                    transaction.setRecipientCard(rs.getString("recipient_card"));
                    transaction.setAmount(rs.getDouble("transaction_amount"));
                    transaction.setTransactionTime(rs.getTimestamp("transaction_time").toLocalDateTime());
                }

                account.setId(rs.getInt("account_id"));
                account.setCardNumber(rs.getString("account_card_number"));
                account.setBalance(rs.getDouble("account_balance"));
                account.setPass(rs.getInt("account_pass"));
                account.setExpirationCardDate(rs.getDate("account_expiration_date_card").toLocalDate());

                if (transaction.getId() != 0) {
                    for (Account item : accounts) {
                        if (account.getId() == item.getId()) {
                            account.addTransaction(transaction);
                            isAccount = true;
                        }
                    }
                    if (!isAccount){
                        account.addTransaction(transaction);
                        accounts.add(account);
                    }
                } else accounts.add(account);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return accounts;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, Account obj) throws DaoException {
        try {
            prSt.setString(1, obj.getCardNumber());
            prSt.setDouble(2, obj.getBalance());
            prSt.setInt(3, obj.getPass());
            prSt.setInt(4, obj.getId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, Account obj) throws DaoException {
        try {
            prSt.setString(1, obj.getCardNumber());
            prSt.setDouble(2, obj.getBalance());
            prSt.setInt(3, obj.getPass());
            prSt.setDate(4, java.sql.Date.valueOf(obj.getExpirationCardDate()));
            prSt.setInt(5, obj.getUserId());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public Account createWithField(int fKey) throws DaoException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter balance: ");
        double balance = in.nextDouble();
        System.out.print("Enter card password: ");
        int pass = Integer.parseInt(in.next());
        Account tempAccount = new Account(fKey, pass);
        tempAccount.setBalance(balance);
        return create(tempAccount);
    }
}
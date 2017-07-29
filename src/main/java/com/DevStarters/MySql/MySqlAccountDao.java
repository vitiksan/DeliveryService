package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.PaymentSystem.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MySqlAccountDao extends AbstractDao<Account,Integer>{

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
        protected void setBalance(double balance) {
            super.setBalance(balance);
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

    @Override
    public String getSelectQuery(){
        return "Select * FROM accounts ac JOIN transaction tr " +
                "ON (tr.sender_account_id=ac.account_id) WHERE account_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "Select * FROM accounts ac JOIN transaction tr " +
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
                "account_pass,account_expiration_date) VALUES(?,?,?,?,?)";
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
                account.setId(rs.getInt("account_id"));
                account.setCardNumber(rs.getString("account_card_number"));
                account.setBalance(rs.getDouble("account_balance"));
                account.setPass(rs.getInt("account_pass"));
                account.setExpirationCardDate(rs.getDate("account_expiration_date").toLocalDate());
                accounts.add(account);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return accounts;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, Account obj) throws DaoException {
        try {
            prSt.setString(1,obj.getCardNumber());
            prSt.setDouble(2,obj.getBalance());
            prSt.setInt(3,obj.getPass());
            prSt.setInt(4,obj.getId());
        } catch (SQLException e) {
           throw new DaoException();
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, Account obj) throws DaoException {
        try {
            prSt.setString(1,obj.getCardNumber());
            prSt.setDouble(2,obj.getBalance());
            prSt.setInt(3,obj.getPass());
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}

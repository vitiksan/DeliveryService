package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoExeption;
import com.DevStarters.Domain.PaymentSystem.Account;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import com.DevStarters.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class MySqlUserDao extends AbstractDao<User, Integer> {
    private class ExtendAccount extends Account {
        public ExtendAccount() {
            super();
        }

        @Override
        protected void setExpirationCardDate(LocalDate expirationCardDate) {
            super.setExpirationCardDate(expirationCardDate);
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
        protected void setBalance(double balance) {
            super.setBalance(balance);
        }

        @Override
        protected void setPass(int pass) {
            super.setPass(pass);
        }
    }

    private class ExtendUser extends User {
        public ExtendUser() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }

        @Override
        protected void setPassword(String password) {
            super.setPassword(password);
        }

        @Override
        protected void setLogin(String login) {
            super.setLogin(login);
        }
    }

    private class ExtendTransaction extends Transaction {
        public ExtendTransaction() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    public MySqlUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM users u JOIN accounts a USING(user_id) JOIN transactions t " +
                "ON(a.account_id=t.sender_account_id) WHERE user_id";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM users u JOIN accounts a USING(user_id) JOIN transactions t " +
                "ON(a.account_id=t.sender_account_id);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET user_name=?,user_surname=?,user_login=?,user_password=?," +
                "user_address=?,user_born_date=? WHERE user_id=?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users VALUES (NULL,?,?,?,?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE user_id=?;";
    }

    @Override
    public ArrayList<User> parsData(ResultSet rs) throws DaoExeption {
        ArrayList<User> users = new ArrayList<User>();
        boolean isUser = false;
        try {
            while (rs.next()) {
                ExtendUser user = new ExtendUser();
                ExtendAccount account = new ExtendAccount();
                ExtendTransaction transaction = new ExtendTransaction();
                transaction.setId(rs.getInt("transaction_id"));
                transaction.setSenderAccountId(rs.getInt("sender_account_id"));
                transaction.setRecipientCard(rs.getString("recipient_card"));
                transaction.setAmount(rs.getDouble("transaction_amount"));
                transaction.setTransactionTime(((LocalDateTime) rs.getObject("transaction_time")));
                account.setId(rs.getInt("account_id"));
                account.setCardNumber(rs.getString("account_card_number"));
                account.setBalance(rs.getDouble("account_balance"));
                account.setPass(rs.getInt("account_password"));
                account.setExpirationCardDate(rs.getDate("account_expiration_date_card").toLocalDate());
                account.addTransaction(transaction);

                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setSurname(rs.getString("user_surname"));
                user.setBornDate(rs.getDate("user_born_date").toLocalDate());
                user.setAddress(rs.getString("user_address"));
                user.setAccount(account);
                for (User item : users) {
                    if (item.getId() == user.getId()) {
                        item.getAccount().addTransaction(user.getAccount().getTransactions().iterator().next());
                        isUser = true;
                    }
                }
                if (!isUser) {
                    users.add(user);
                }
            }
        } catch (Exception e) {
            throw new DaoExeption(e);
        }
        return users;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, User obj) throws DaoExeption {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getSurname());
            prSt.setString(3, obj.getLogin());
            prSt.setString(4, obj.getPassword());
            prSt.setString(5, obj.getAddress());
            prSt.setDate(6, java.sql.Date.valueOf(obj.getBornDate()));
            prSt.setInt(7, obj.getId());
        } catch (Exception e) {
            throw new DaoExeption(e);
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, User obj) throws DaoExeption {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getSurname());
            prSt.setString(3, obj.getLogin());
            prSt.setString(4, obj.getPassword());
            prSt.setString(5, obj.getAddress());
            prSt.setDate(6, java.sql.Date.valueOf(obj.getBornDate()));
        } catch (Exception e) {
            throw new DaoExeption(e);
        }
    }
}

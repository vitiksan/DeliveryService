package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
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
import java.util.Scanner;

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
        return "SELECT * FROM users u LEFT JOIN accounts a USING(user_id) LEFT JOIN transactions t " +
                "ON(a.account_id=t.sender_account_id) WHERE user_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM users u LEFT JOIN accounts a USING(user_id) LEFT JOIN transactions t " +
                "ON(a.account_id=t.sender_account_id);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET user_name=?,user_surname=?,user_login=?,user_password=?," +
                "user_address=?,user_born_date=? WHERE user_id=?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (user_name,user_surname,user_login,user_password,user_address," +
                "user_born_date) VALUES (?,?,?,?,?,?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE user_id=?;";
    }

    @Override
    public ArrayList<User> parsData(ResultSet rs) throws DaoException {
        ArrayList<User> users = new ArrayList<User>();
        try {
            while (rs.next()) {
                boolean isAccount = false;
                boolean isUser = false;
                ExtendUser user = new ExtendUser();
                ExtendAccount account = new ExtendAccount();
                ExtendTransaction transaction = new ExtendTransaction();

                if (rs.getTimestamp("transaction_time") != null) {
                    transaction.setId(rs.getInt("transaction_id"));
                    transaction.setSenderAccountId(rs.getInt("sender_account_id"));
                    transaction.setRecipientCard(rs.getString("recipient_card"));
                    transaction.setAmount(rs.getDouble("transaction_amount"));
                    transaction.setTransactionTime(rs.getTimestamp("transaction_time").toLocalDateTime());
                }
                if (rs.getDate("account_expiration_date_card") != null) {
                    account.setId(rs.getInt("account_id"));
                    account.setCardNumber(rs.getString("account_card_number"));
                    account.setBalance(rs.getDouble("account_balance"));
                    account.setPass(rs.getInt("account_pass"));
                    account.setExpirationCardDate(rs.getDate("account_expiration_date_card").toLocalDate());
                    account.setUserId(rs.getInt("user_id"));
                }

                if (account.getId() != 0) {
                    for (Account account1 : user.getAccounts()) {
                        if (account.getId() == account1.getId()) {
                            if (transaction.getId() != 0)
                                account1.addTransaction(transaction);
                            isAccount = true;
                        }
                    }
                    if (!isAccount) {
                        if (transaction.getId() != 0)
                            account.addTransaction(transaction);
                    }
                }

                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setSurname(rs.getString("user_surname"));
                user.setLogin(rs.getString("user_login"));
                user.setPassword(rs.getString("user_password"));
                user.setBornDate(rs.getDate("user_born_date").toLocalDate());
                user.setAddress(rs.getString("user_address"));

                for (User user1 : users) {
                    if (user1.getId() == user.getId()) {
                        if (account.getId() != 0) {
                            user1.addAccount(account);
                        }
                    }
                }
                if (!isUser) {
                    if (account.getId() != 0)
                        user.addAccount(account);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, User obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getSurname());
            prSt.setString(3, obj.getLogin());
            prSt.setString(4, obj.getPassword());
            prSt.setString(5, obj.getAddress());
            prSt.setDate(6, java.sql.Date.valueOf(obj.getBornDate()));
            prSt.setInt(7, obj.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, User obj) throws DaoException {
        try {
            prSt.setString(1, obj.getName());
            prSt.setString(2, obj.getSurname());
            prSt.setString(3, obj.getLogin());
            prSt.setString(4, obj.getPassword());
            prSt.setString(5, obj.getAddress());
            prSt.setDate(6, java.sql.Date.valueOf(obj.getBornDate()));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User createWithField(int fKey) throws DaoException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = in.nextLine();
        System.out.print("Enter surname: ");
        String surname = in.nextLine();
        System.out.print("Enter login: ");
        String login = in.nextLine();
        System.out.print("Enter password: ");
        String password = in.nextLine();
        System.out.print("Enter address: ");
        String address = in.nextLine();
        System.out.print("Enter year of born: ");
        int year = Integer.parseInt(in.next());
        System.out.print("Enter month of born: ");
        int month = Integer.parseInt(in.next());
        System.out.print("Enter day of born: ");
        int day = Integer.parseInt(in.next());
        User tempUser = new User(name, surname, login, password, address, year, month, day);
        return create(tempUser);
    }
}
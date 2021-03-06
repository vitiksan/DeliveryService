package com.DevStarters.MySql;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.PaymentSystem.Transaction;
import org.apache.commons.lang.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MySqlTransactionDao extends AbstractDao<Transaction,Integer> {

    private class ExtendTransaction extends Transaction{

        public ExtendTransaction() {
            super();
        }

        @Override
        protected void setId(int id) {
            super.setId(id);
        }
    }

    public MySqlTransactionDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM transactions WHERE transaction_id=";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM transactions;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE transactions SET sender_account_id=?,recipient_card=?,transaction_amount=?," +
                "order_id=? WHERE transaction_id=?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO transactions (sender_account_id, recipient_card," +
                "transaction_amount,transaction_time,order_id)VALUES (?,?,?,NOW(),?);";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM transactions WHERE transaction_id=?;";
    }

    @Override
    public ArrayList<Transaction> parsData(ResultSet rs) throws DaoException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            while (rs.next()) {
                ExtendTransaction transaction = new ExtendTransaction();
                transaction.setId(rs.getInt("transaction_id"));
                transaction.setSenderAccountId(rs.getInt("sender_account_id"));
                transaction.setRecipientCard(rs.getString("recipient_card"));
                transaction.setAmount(rs.getInt("transaction_id"));
                transaction.setTransactionTime(rs.getTimestamp("transaction_time").toLocalDateTime());
                transaction.setOrderId(rs.getInt("order_id"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return transactions;
    }

    @Override
    public void parsUpdate(PreparedStatement prSt, Transaction obj) throws DaoException {
        try {
            prSt.setInt(1,obj.getSenderAccountId());
            prSt.setString(2,obj.getRecipientCard());
            prSt.setDouble(3,obj.getAmount());
            prSt.setInt(4,obj.getOrderId());
            prSt.setInt(5, obj.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void parsInsert(PreparedStatement prSt, Transaction obj) throws DaoException {
        try {
            prSt.setInt(1,obj.getSenderAccountId());
            prSt.setString(2,obj.getRecipientCard());
            prSt.setDouble(3,obj.getAmount());
            prSt.setInt(4,obj.getOrderId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Transaction createWithField(int fKey) throws DaoException {
        throw new NotImplementedException();
    }
}

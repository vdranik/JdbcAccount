package ua.jdbcaccount.dao.impl;

import ua.jdbcaccount.dao.AccountDao;
import ua.jdbcaccount.model.Account;
import ua.jdbcaccount.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 8/9/2017.
 */
public class AccountDaoImpl implements AccountDao {

    private ConnectionManager connectionManager;

    @Override
    public Account getAccountById(int accountId) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            Account account = null;
            if (resultSet.next()) {
                account = createAccount(resultSet);
            } else {
                throw new RuntimeException("Cannot find account whith id=" + accountId);
            }

            resultSet.close();
            statement.close();
            return account;

        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB: " + e);
        } finally {

        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE account SET money = ? WHERE id = ?");
            statement.setDouble(1, account.getMoney());
            statement.setInt(2, account.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB: " + e);
        }
    }

    private Account createAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setMoney(resultSet.getDouble("money"));
        return account;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}

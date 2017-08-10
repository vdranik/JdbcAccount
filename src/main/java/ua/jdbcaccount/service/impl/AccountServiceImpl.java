package ua.jdbcaccount.service.impl;

import ua.jdbcaccount.dao.AccountDao;
import ua.jdbcaccount.model.Account;
import ua.jdbcaccount.service.AccountService;
import ua.jdbcaccount.util.ConnectionManager;
import ua.jdbcaccount.util.NotEnoughMoneyException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by User on 8/9/2017.
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    private ConnectionManager connectionManager;

    @Override
    public void changeAccountBalance(int accountId, double amountOfMoney, Operation operation) {
        try {
            Connection connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            SQLException savedException = null;

            try {
                Account account = accountDao.getAccountById(accountId);
                double currentBalance = account.getMoney();

                switch (operation) {
                    case ADD:
                        account.setMoney(currentBalance + amountOfMoney);
                        break;
                    case WITHDRAW:
                        if (account.getMoney() < amountOfMoney) {
                            throw new NotEnoughMoneyException("You don't have enough money! Your current balance " + currentBalance);
                        } else account.setMoney(currentBalance - amountOfMoney);
                        break;
                }

                accountDao.updateAccount(account);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                savedException = ex;
            } finally {
                connection.setAutoCommit(true);
                if (savedException != null) {
                    throw savedException;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Account getAccountById(int accountId) {
        return accountDao.getAccountById(accountId);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}

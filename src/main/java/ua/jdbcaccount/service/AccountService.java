package ua.jdbcaccount.service;

import ua.jdbcaccount.model.Account;
import ua.jdbcaccount.service.impl.Operation;

import java.sql.SQLException;

/**
 * Created by User on 8/9/2017.
 */
public interface AccountService {

    Account getAccountById(int accountId);
    void changeAccountBalance(int accountId, double amountOfMoney, Operation operation);

}

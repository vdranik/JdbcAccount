package ua.jdbcaccount.dao;

import ua.jdbcaccount.model.Account;

import java.util.List;

/**
 * Created by User on 8/9/2017.
 */
public interface AccountDao {

    Account getAccountById(int accountId);
    void updateAccount(Account account);

//    void addAccount(Account account);
//    void deleteAccount(Account account);
//    List<Account> getAllAccounts();
}

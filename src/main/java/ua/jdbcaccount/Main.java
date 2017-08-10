package ua.jdbcaccount;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.jdbcaccount.config.AppConfig;
import ua.jdbcaccount.service.AccountService;
import ua.jdbcaccount.service.impl.AccountServiceImpl;
import ua.jdbcaccount.service.impl.Operation;
import ua.jdbcaccount.util.ConnectionManager;

/**
 * Created by max on 7/14/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AccountService accountService = context.getBean(AccountServiceImpl.class);
        ConnectionManager connectionManager = context.getBean(ConnectionManager.class);

        accountService.changeAccountBalance(1, 1000d, Operation.ADD);
        accountService.changeAccountBalance(2, 1000d, Operation.WITHDRAW);
        accountService.changeAccountBalance(3, 9d, Operation.WITHDRAW);

        System.out.println("Account 1 balance " + accountService.getAccountById(1).getMoney());
        System.out.println("Account 2 balance " + accountService.getAccountById(2).getMoney());
        System.out.println("Account 3 balance " + accountService.getAccountById(3).getMoney());

        //throw NotEnoughMoneyException
        accountService.changeAccountBalance(3, 4000d, Operation.WITHDRAW);

        connectionManager.closeConnection();
    }
}

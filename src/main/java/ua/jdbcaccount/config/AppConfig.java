package ua.jdbcaccount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.jdbcaccount.dao.AccountDao;
import ua.jdbcaccount.dao.impl.AccountDaoImpl;
import ua.jdbcaccount.service.AccountService;
import ua.jdbcaccount.service.impl.AccountServiceImpl;
import ua.jdbcaccount.util.ConnectionManager;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Created by max on 7/14/17.
 */
@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/init.sql")
                .build();
    }

    @Bean
    public ConnectionManager connectionManager(){
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.setDataSource(dataSource());
        return connectionManager;
    }

    @Bean
    public AccountDao accountDao() throws SQLException {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        accountDao.setConnectionManager(connectionManager());
        return accountDao;
    }

    @Bean
    public AccountService accountService() throws SQLException {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.setAccountDao(accountDao());
        accountService.setConnectionManager(connectionManager());
        return accountService;
    }
}

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ua.jdbcaccount.dao.AccountDao;
import ua.jdbcaccount.model.Account;
import ua.jdbcaccount.service.AccountService;
import ua.jdbcaccount.service.impl.AccountServiceImpl;
import ua.jdbcaccount.service.impl.Operation;
import ua.jdbcaccount.util.ConnectionManager;
import ua.jdbcaccount.util.NotEnoughMoneyException;

import static org.mockito.Mockito.*;

/**
 * Created by User on 8/10/2017.
 */
public class AccountServiceImplTest {

    @Mock private AccountDao accountDao;
    @Mock private ConnectionManager connectionManager;
    @Mock private Account account;
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testGetAccountById(){
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.setAccountDao(accountDao);
        accountService.setConnectionManager(connectionManager);
        accountService.getAccountById(1);
        Mockito.verify(accountDao).getAccountById(1);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testNotEnoughMoneyException(){
        long amountOfMoney = 200L;
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.setAccountDao(accountDao);
        accountService.setConnectionManager(connectionManager);

        when(accountService.getAccountById(1)).thenReturn(account);
        when(account.getMoney()).thenReturn(100d).thenThrow(new NotEnoughMoneyException("You don't have enough money! Your current balance " + 100));
        //todo

        }

}

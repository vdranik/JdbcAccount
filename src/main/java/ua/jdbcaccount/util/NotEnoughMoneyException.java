package ua.jdbcaccount.util;

/**
 * Created by User on 8/10/2017.
 */
public class NotEnoughMoneyException extends RuntimeException{

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}

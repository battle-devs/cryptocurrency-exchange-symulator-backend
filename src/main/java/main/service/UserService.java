package main.service;

import java.math.BigDecimal;
import java.util.List;

import main.entity.Currency;
import main.entity.User;
import main.exception.DuplicateUsernameException;
import main.exception.InsufficientFundsException;

public interface UserService {

    User addAsset(String userName, Currency currency, BigDecimal amount);

    User addUser(User newUser) throws DuplicateUsernameException;

    /**
     * This is method for updating user info
     *
     * @param userId     of user
     * @param updateUser data for update
     * @return User after update
     */
    User updateUser(Long userId, User updateUser);

    /**
     * This is method for looking User by his username
     *
     * @param username of user to be found
     * @return User if found
     */
    User findByUsername(String username);

    /**
     * This is method for getting all users
     *
     * @return List<User> list of all users
     */
    List<User> getAllUsers();

    /**
     * This is method for looking User by his id
     *
     * @param userId id of a user
     * @return User if found
     */
    User findById(Long userId);

    User changeUserPassword(long username, String user);

    User disableUser(long userId) throws Exception;

    User resetUser(String userName);

    User substractFromAsset(String userName, Currency currency, BigDecimal amount) throws InsufficientFundsException;

    String removeUser(String userName);

    User plnToUsd(String userName);

    User usdToPln(String userName);
}

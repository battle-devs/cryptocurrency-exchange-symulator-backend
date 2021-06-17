package main.service;

import java.util.List;

import main.entity.Account;
import main.exception.DuplicateAccountException;

public interface AccountService {

    Account addAccount(Account newAcc) throws DuplicateAccountException;

    Account updateUser(Long accId, Account updateAcc);

    Account findByUsername(String username);

    List<Account> getAllAccounts();

    Account findById(Long accId);

}

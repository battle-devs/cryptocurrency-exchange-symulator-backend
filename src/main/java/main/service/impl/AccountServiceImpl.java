package main.service.impl;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.entity.Account;
import main.entity.AccountName;
import main.exception.DuplicateAccountException;
import main.repository.AccountsRepository;
import main.service.AccountService;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("accountService")
@Transactional
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Account addAccount(Account newAcc) throws DuplicateAccountException {
        Assert.notNull(newAcc, "Dane nie mogą być puste");
        if (accountsRepository.findByNickName(
                Optional.ofNullable(newAcc.getName())
                        .map(AccountName::getNickName)
                        .orElse(null))
                != null) {
            throw new DuplicateAccountnameException(
                    "Konto o takim adresie email już istnieje " + newAcc.getName().getNickName());
        }
        newAcc.setPassword(passwordEncoder.encode(newAcc.getPassword()));
        if (newAcc.getRole() == null) {
            newAcc.setRole(AuthoritiesConstants.Account);
        } else if (AuthoritiesConstants.ADMIN.equalsIgnoreCase(newAcc.getRole())) {
            newAcc.setRole(AuthoritiesConstants.ADMIN);
        } else {
            newAcc.setRole(AuthoritiesConstants.Account);
        }
        try {
            accountsRepository.save(newAcc);
        } catch (Exception e) {
            throw new HibernateException(
                    "Wystąpił problem z rejestracją użytkownika " + newAcc.getAccountname());
        }
        return newAcc;
    }

    /* (non-Javadoc)
     * @see pl.kohutmariusz.authserver.service.AccountService#updateAccount(java.lang.String, pl.kohutmariusz.authserver.domain.Account)
     */
    @Override
    public Account updateAccount(Long AccountId, Account newAcc) {
        Assert.notNull(newAcc, "Dane nie mogą być puste");
        Account Account = accountsRepository.findOne(AccountId);
        updateAccountData(Account, newAcc);
        try {
            accountsRepository.save(Account);
        } catch (HibernateException e) {
            throw new HibernateException(
                    "Wystąpił problem z aktualizowaniem użytkownika " + newAcc.getAccountname());
        }
        return Account;
    }

    private void updateAccountData(Account Account, Account newAcc) {
        if (newAcc.getAccountname() != null) {
            Account.setAccountname(newAcc.getAccountname());
        }
        if (newAcc.getFirstName() != null) {
            Account.setFirstName(newAcc.getFirstName());
        }
        if (newAcc.getLastName() != null) {
            Account.setLastName(newAcc.getLastName());
        }
        if (newAcc.getPhoneNumber() != null) {
            Account.setPhoneNumber(newAcc.getPhoneNumber());
        }
        if (newAcc.getRole() != null) {
            Account.setRole(newAcc.getRole());
        }
    }

    /* (non-Javadoc)
     * @see pl.kohutmariusz.authserver.service.AccountService#findByAccountname(java.lang.String)
     */
    @Override
    public Account findByAccountname(String email) {
        Assert.notNull(email, "Email must not be null");
        return accountsRepository.findByAccountname(email);
    }

    /* (non-Javadoc)
     * @see pl.kohutmariusz.authserver.service.AccountService#getAllAccounts()
     */
    @Override
    public List<Account> getAllAccounts() {
        return accountsRepository.findAll();
    }

    /* (non-Javadoc)
     * @see pl.kohutmariusz.authserver.service.AccountService#findById(java.lang.Long)
     */
    @Override
    public Account findById(Long AccountId) {
        return accountsRepository.findOne(AccountId);
    }

    @Override
    public Account changeAccountPassword(long AccountId, String newPassword) {
        Assert.notNull(newPassword, "Hasło nie może być puste");
        Account Account = accountsRepository.findOne(AccountId);
        Account.setPassword(passwordEncoder.encode(newPassword));
        try {
            accountsRepository.save(Account);
        } catch (HibernateException e) {
            throw new HibernateException(
                    "Wystąpił problem ze zmiana hasła dla użytkownika o id: " + AccountId);
        }
        return Account;
    }

    @Override
    public Account disableAccount(long AccountId) throws Exception {
        try {
            Account Account = accountsRepository.findOne(AccountId);
            Account.setEnabled(false);
            return accountsRepository.save(Account);
        } catch (Exception e) {
            throw new Exception("Problem z wyłączeniem użytkownika, sprawdź dane.");
        }
    }
}
package main.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.configuration.AuthoritiesConstants;
import main.entity.Asset;
import main.entity.Currency;
import main.entity.User;
import main.exception.DuplicateUsernameException;
import main.repository.AssetRepository;
import main.repository.CurrencyRepository;
import main.repository.UserRepository;
import main.service.UserService;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("accountService")
@Transactional
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final CurrencyRepository currencyRepository;
    private final PasswordEncoder passwordEncoder;

    public User addAsset(String userName, Currency currency, BigDecimal amount) {

        if (currency.getId() == null) {
            currencyRepository.save(currency);
        } else {
            if (currencyRepository.findById(currency.getId()).isEmpty()) {
                currencyRepository.save(currency);
            }
        }

        User user = userRepository.findByUserName(userName);

        if (user.getAsset().size() == 0) {
            Asset newAsset = new Asset();
            newAsset.setAmount(amount);
            newAsset.setCurrency(currency);
            List<Asset> userAssets = new ArrayList<>();
            userAssets.add(newAsset);
            user.setAsset(userAssets);
            return updateUser(user.getId(), user);
        }

        return Optional.ofNullable(user)
                .map(User::getAsset)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(ass -> ass.getCurrency().getName().equalsIgnoreCase(currency.getName()))
                .findAny()
                .map(ass -> updateUserAsset(ass, user, amount, currency))
                .map(usr -> updateUser(usr.getId(), usr))
                .orElseGet(() -> {
                    Asset newAsset = new Asset();
                    newAsset.setAmount(amount);
                    newAsset.setCurrency(currency);
                    List<Asset> userAssets = user.getAsset();
                    userAssets.add(newAsset);
                    user.setAsset(userAssets);
                    return updateUser(user.getId(), user);
                });

    }

    private User updateUserAsset(Asset ass, User user, BigDecimal amount, Currency currency) {
        List<Asset> userAssets = user.getAsset();
        Asset newAsset = new Asset();
        return user.getAsset().stream()
                .filter(asset -> asset.equals(ass))
                .findAny()
                .map(asset -> {
                    asset.setAmount(asset.getAmount().add(amount));
                    userAssets.remove(ass);
                    userAssets.add(asset);
                    user.setAsset(userAssets);
                    return user;
                })
                .orElseGet(() -> {
                    newAsset.setCurrency(currency);
                    newAsset.setAmount(amount);
                    userAssets.add(newAsset);
                    user.setAsset(userAssets);
                    return user;
                });
    }


    public User substractAsset() {
        return null;
    }

    @Override
    public User addUser(User newUser) throws DuplicateUsernameException {
        Assert.notNull(newUser, "Dane nie mogą być puste");
        if (userRepository.findByUserName(newUser.getUsername()) != null) {
            throw new DuplicateUsernameException(
                    "Konto o takiej nazwie już istnieje " + newUser.getUsername());
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (newUser.getRole() == null) {
            newUser.setRole(AuthoritiesConstants.USER);
        } else if (AuthoritiesConstants.ADMIN.equalsIgnoreCase(newUser.getRole())) {
            newUser.setRole(AuthoritiesConstants.ADMIN);
        } else {
            newUser.setRole(AuthoritiesConstants.USER);
        }
        try {
            userRepository.save(newUser);
        } catch (Throwable e) {
            throw new HibernateException(
                    "Wystąpił problem z rejestracją użytkownika " + newUser.getUsername());
        }
        return newUser;
    }

    @Override
    public User updateUser(Long userId, User newUser) {
        Assert.notNull(newUser, "Dane nie mogą być puste");
        User user = userRepository.findById(userId).orElse(null);
        updateUserData(user, newUser);
        try {
            userRepository.save(user);
        } catch (HibernateException e) {
            throw new HibernateException(
                    "Wystąpił problem z aktualizowaniem użytkownika " + newUser.getUsername());
        }
        return user;
    }

    private void updateUserData(User user, User newUser) {
        if (newUser.getUsername() != null) {
            user.setUserName(newUser.getUsername());
        }
        if (newUser.getFirstName() != null) {
            user.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }
        if (newUser.getRole() != null) {
            user.setRole(newUser.getRole());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
    }

    @Override
    public User findByUsername(String userName) {
        Assert.notNull(userName, "Username must not be null");
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User changeUserPassword(long userId, String newPassword) {
        Assert.notNull(newPassword, "Hasło nie może być puste");
        User user = userRepository.findById(userId).orElse(new User());
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            userRepository.save(user);
        } catch (HibernateException e) {
            throw new HibernateException(
                    "Wystąpił problem ze zmiana hasła dla użytkownika o id: " + userId);
        }
        return user;
    }

    @Override
    public User disableUser(long userId) throws Exception {
        try {
            User user = userRepository.findById(userId).orElse(new User());
            user.setIsEnable(false);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Problem z wyłączeniem użytkownika, sprawdź dane.");
        }
    }
}
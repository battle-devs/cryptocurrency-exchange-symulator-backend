package main.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import main.configuration.JWTAuthenticationHelper;
import main.configuration.SecurityConstants;
import main.entity.Currency;
import main.entity.User;
import main.exception.DuplicateUsernameException;
import main.dto.AuthenticationTransferObject;
import main.dto.Credentials;
import main.dto.UserPasswordChangeRequest;
import main.exception.InsufficientFundsException;
import main.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JWTAuthenticationHelper jwtFilter;

    @Autowired
    public UserController(
            UserService userService,
            AuthenticationManager authManager,
            JWTAuthenticationHelper jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @PutMapping(value = "/reset/{userName}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Resetowanie środków użyszkodnika")
    public User resetAssets(@PathVariable String userName) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: user " + userName + " has been reset");
        }

        return userService.resetUser(userName);
    }

    @PostMapping(value = "/usdToPln/{userName}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Zmiana na PLN")
    public User changeUsdToPln(@PathVariable String userName) {
        return userService.usdToPln(userName);
    }

    @PostMapping(value = "/plnToUsd/{userName}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Zmiana na USD")
    public User changePlnToUsd(@PathVariable String userName) {
        return userService.plnToUsd(userName);
    }

    @PostMapping(value = "/substractAsset/{userName}/{amount}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Odejmowanie hajsu userowi")
    public User subMoney(@PathVariable String userName, @PathVariable BigDecimal amount, @RequestBody Currency currency) throws InsufficientFundsException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: substracted " + amount + "  from, " + userName + " account");
        }
        return userService.substractFromAsset(userName, currency, amount);
    }

    @PostMapping(value = "/addAsset/{userName}/{amount}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Dodawanie hajsu userowi")
    public User addMoney(@PathVariable String userName, @PathVariable BigDecimal amount, @RequestBody Currency currency) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: added " + amount + "  to, " + userName + " account");
        }
        return userService.addAsset(userName, currency, amount);
    }

    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Dodawanie usera / Rejestracja")
    public User registerUser(@RequestBody User user) throws DuplicateUsernameException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: registerUser, " + user);
        }
        return userService.addUser(user);
    }

    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Pobieranie wszystkich użytkowników")
    public List<User> getAllUsers() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: getAllUsers");
        }
        return userService.getAllUsers();
    }

    @PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update usera z danym Id")
    public User updateUser(@RequestBody User user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: updateUser, " + user);
        }
        return userService.updateUser(user.getId(), user);
    }

    @DeleteMapping(value = "/removeUser/{userName}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Usuwanie konta")
    public String removeUser(@PathVariable String userName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: removed, " + userName);
        }
        return userService.removeUser(userName);
    }

    @PutMapping(
            value = "/user/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Zmiana hasła użytkownika")
    public User changeUserPassword(@PathVariable long userId,
                                   @RequestBody UserPasswordChangeRequest changeRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: changeUserPassword, " + userId);
        }
        return userService.changeUserPassword(userId, changeRequest.getNewPassword());
    }

    @PutMapping(
            value = "/user/disableUser/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Ustawianie flagi isEnable na false")
    public User disableUser(@PathVariable long userId) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: changeUserPassword, " + userId);
        }
        return userService.disableUser(userId);
    }

    @GetMapping(value = "/user/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Wyszukaj uzytkownika z danym Id")
    public User getUser(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AuthenticationTransferObject> login(@RequestBody Credentials authUser) {
        User logedUser = userService.findByUsername(authUser.getUsername());
        if (logedUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
        }
        final Authentication authentication =
                jwtFilter.attemptAuthentication(authUser, logedUser.getAuthorities());
        // Inject into security context
        String token = jwtFilter.generateToken(authentication);
        return ResponseEntity.ok(prepareResponse(logedUser, token));
    }

    private AuthenticationTransferObject prepareResponse(User logedUser, String token) {
        AuthenticationTransferObject authResponse = new AuthenticationTransferObject();
        authResponse.setUsername(logedUser.getUsername());
        authResponse.setName(logedUser.getFirstName());
        authResponse.setLastname(logedUser.getLastName());
        authResponse.setId(logedUser.getId());
        authResponse.setToken(SecurityConstants.TOKEN_PREFIX.value() + token);
        authResponse.setRole(logedUser.getRole());
        return authResponse;
    }

}

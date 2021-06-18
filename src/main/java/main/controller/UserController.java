package main.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.List;

import main.configuration.JWTAuthenticationHelper;
import main.configuration.SecurityConstants;
import main.entity.User;
import main.exception.DuplicateUsernameException;
import main.model.AuthenticationTransferObject;
import main.model.Credentials;
import main.model.UserPasswordChangeRequest;
import main.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/user", description = "Operations about users")
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

    @ApiOperation(value = "Register new user", response = User.class)
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user) throws DuplicateUsernameException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: registerUser, " + user);
        }
        return userService.addUser(user);
    }

    @ApiOperation(value = "Get list of all users", response = User.class, responseContainer = "List")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved list"),
                    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"
                    ),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " UserController: getAllUsers");
        }
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Update user data", response = User.class)
    @PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: updateUser, " + user);
        }
        return userService.updateUser(user.getId(), user);
    }

    @ApiOperation(value = "Change user password", response = User.class)
    @PutMapping(
            value = "/user/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public User changeUserPassword(@PathVariable long userId,
                                   @RequestBody UserPasswordChangeRequest changeRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: changeUserPassword, " + userId);
        }
        return userService.changeUserPassword(userId, changeRequest.getNewPassword());
    }

    @ApiOperation(value = "Disable user", response = User.class)
    @PutMapping(
            value = "/user/disableUser/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public User disableUser(@PathVariable long userId) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LocalDateTime.now() + " User controller: changeUserPassword, " + userId);
        }
        return userService.disableUser(userId);
    }

    @ApiOperation(value = "Find user by id", response = User.class)
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationTransferObject> login(@RequestBody Credentials authUser) {
        User logedUser = userService.findByUsername(authUser.getUsername());
        if (logedUser == null) {
            throw new BadCredentialsException("Bad credentials");
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

package main.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;

import main.configuration.AuthoritiesConstants;
import main.configuration.JWTAuthenticationHelper;
import main.entity.User;
import main.service.UserService;
import main.service.impl.CurrentUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private UserService userService;

    @MockBean private CurrentUserDetailsService currentUserDetailsService;

    @MockBean private JWTAuthenticationHelper jwtAuthenticationHelper;

    @Test
    @WithMockUser(username = "admin", password = "password1")
    public void registerUser() throws Exception {
        User registrationUser = new User();
        registrationUser.setUserName("admin");
        registrationUser.setPassword("password");
        registrationUser.setRole(AuthoritiesConstants.ADMIN);

        when(userService.addUser(any(User.class))).thenReturn(registrationUser);

        mvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(registrationUser)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).addUser(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser(username = "admin", password = "password1")
    public void getAllUsers() throws Exception {
        User registrationUser = new User();
        registrationUser.setUserName("admin");
        registrationUser.setPassword("password");
        registrationUser.setRole(AuthoritiesConstants.ADMIN);

        List<User> allUsers = Arrays.asList(registrationUser);

        when(userService.getAllUsers()).thenReturn(allUsers);

        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is(registrationUser.getUsername())));
    }
}

package main.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import main.entity.User;
import main.exception.DuplicateUsernameException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private UserService userService;
    private User testUser;

    @Before
    public void setUp() throws DuplicateUsernameException {
        userService = Mockito.mock(UserService.class);
        testUser = new User();
        testUser.setUserName("admin");
        testUser.setPassword("password");
        testUser.setRole("ADMIN");
        when(userService.addUser(any(User.class))).thenReturn(testUser);
    }

    @Test
    public void addUser() throws Exception {
        User user = userService.addUser(testUser);
        assertNotNull(user);
    }

    @Test
    public void updateUser() {
        User newUser = new User();
        newUser.setUserName("devs@gmail.com");
        newUser.setPassword("password123");
        newUser.setRole("SLAVE");
        Long id = 1L;
        when(userService.updateUser(id, newUser)).thenReturn(newUser);
        User user = userService.updateUser(id, newUser);

        assertNotNull(user);
        assertEquals("devs@gmail.com", user.getUsername());
    }
}

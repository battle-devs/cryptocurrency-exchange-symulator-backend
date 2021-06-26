package main.repository;

import main.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired private UserRepository userRepository;

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User();
        testUser.setUserName("Tytus");
        testUser.setFirstName("Kapitan");
        testUser.setLastName("Bomba");
        testUser.setUser_password("password");
        testUser.setPassword("password");
        testUser.setRole("ADMIN");
        testUser.setEmail("devs@gmail.com");
        userRepository.save(testUser);
    }

    @Test
    public void addNewUser() {
        userRepository.save(testUser);

        User user = userRepository.findByUserName("Tytus");

        assertNotNull(user);
        assertEquals("Tytus", user.getUsername());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addUserWithNullValues() {
        User user = new User();
        user.setUserName(null);
        user.setPassword(null);
        user.setRole(null);
        userRepository.save(user);
    }

    @Test
    public void updateUser() {
        String expectedEmail = "devs@gmail.com";
        User user = userRepository.findByUserName("Tytus");
        user.setUserName(expectedEmail);

        userRepository.save(user);
        User updatedUser = userRepository.findByUserName(expectedEmail);

        assertEquals(1, userRepository.findAll().size());
        assertNotNull(updatedUser);
        assertEquals(expectedEmail, updatedUser.getUsername());
    }
}

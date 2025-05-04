package org.example.pmas.service;

import org.example.pmas.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback // Ensures database changes are rolled back after each test
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getAll() {
        // arrange (nothing to set up, using existing data from h2Init)

        // act
        List<User> users = userService.getAll();

        // assert
        assertThat(users).isNotEmpty();
        assertThat(users).extracting(User::getName)
                .contains("Rebecca Black", "John Smith", "CharlieXcX");
    }

    @Test
    void getUser() {
        // arrange (we know user with ID 1 is Rebecca Black)

        // act
        User user = userService.getUser(1);

        // assert
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Rebecca Black");
        assertThat(user.getEmail()).isEqualTo("Rebecca@example.com");
    }

    @Test
    void logIn() {
        // arrange
        String email = "Rebecca@example.com";
        String password = "password123";

        // act
        User user = userService.logIn(email, password);

        // assert
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Rebecca Black");
    }
}

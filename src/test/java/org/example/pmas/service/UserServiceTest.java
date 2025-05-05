package org.example.pmas.service;

import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<User> users;
    private User user;
    List<Integer> userIDs;



    @BeforeEach
    void setUp() {
        users = MockDataModel.usersWithValues();
        user = MockDataModel.userWithValues();
        user.setPassword("password");
        userIDs = new ArrayList<>(List.of(1,2,3));
    }


    @Test
    void getAll() {
        // Arrange
        when(userRepository.readAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(users, result);
        verify(userRepository, times(1)).readAll();
    }

    @Test
    void getUser() {
        // Arrange
        when(userRepository.readSelected(1)).thenReturn(user);

        // Act
        User result = userService.getUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).readSelected(1);
    }

    @Test
    void logIn_validCredentials_returnsUser() {
        // Arrange
        String email = "email";
        String password = "password";
        when(userRepository.getByEmail(email)).thenReturn(user);

        // Act
        User result = userService.logIn(email,password);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).getByEmail(email);
    }

}

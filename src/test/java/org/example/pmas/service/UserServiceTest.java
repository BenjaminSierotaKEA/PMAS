package org.example.pmas.service;

import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ITaskRepository taskRepository;
    @Mock
    private IProjectRepository projectRepository;

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
        List<Task> task = MockDataModel.tasksWithValues();
        user.setTasks(task);
        when(userRepository.readUserWithDetails(1)).thenReturn(user);

        // Act
        User result = userService.getUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).readUserWithDetails(1);
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

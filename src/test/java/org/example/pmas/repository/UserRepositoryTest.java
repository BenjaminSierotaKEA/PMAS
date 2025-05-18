package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.example.pmas.model.Role;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /*
    @Test
    void create() {
        // Arrange
        var user = new User("Jane Doe", "jane@example.com", "password123", new Role(), "picture.jpg");

        // Act
        var actual = userRepository.create(user);
        var expected = userRepository.readSelected(user.getUserID());

        // Assert
        assertEquals(expected, actual);
    }
     */

    @Test
    void readAll() {
        int expectedSize = 3;

        // Act
        var actual = userRepository.readAll();

        // Assert
        assertEquals(expectedSize, actual.size());
    }

    /*
    @Test
    void readSelected() {
        // Arrange
        var expected = new User(1, "Rebecca Black", "email", "password",new Role(),"picture");

        // Act
        var actual = userRepository.readSelected(1);

        // Assert
        assertEquals(expected, actual);

    }

     */

    @Test
    void delete() {
        // Arrange
        int userId = 1;

        // Act
        boolean actual = userRepository.delete(userId);

        // Assert
        assertTrue(actual);
    }

    @Test
    void update() {
        User oldUser = userRepository.readSelected(1);
        User updatedUser = new User("Updated Name", "updated@example.com", oldUser.getPassword(), new Role(3,"Employee"), "picture.jpg");
        updatedUser.setUserID(oldUser.getUserID());

        // Act
        boolean result = userRepository.update(updatedUser);
        User fetchedUser = userRepository.readSelected(updatedUser.getUserID());

        // Assert
        assertTrue(result);
        assertEquals("Updated Name", fetchedUser.getName());
    }

    @Test
    void getByEmail() {
        // Arrange
        String email = "rebecca@example.com";

        // Act
        User actual = userRepository.getByEmail(email);

        // Assert
        assertEquals("Rebecca Black", actual.getName());
    }

    @Test
    void readUserWithDetails_returnsUserWithTasksAndProjects() {
        // Arrange
        int userId = 1;

        // Act
        User user = userRepository.readUserWithDetails(userId);

        //Assert
        //checking the user:
        assertNotNull(user, "Expected user to be found, but got null.");
        assertEquals("Rebecca Black", user.getName());
        assertNotNull(user.getRole());
        assertEquals("Admin", user.getRole().getName());

        //Assert
        // checking if task list was read:
        assertNotNull(user.getTasks());
        assertFalse(user.getTasks().isEmpty());

        //take a task from the list and see if it was assigned information
        Boolean taskHasContents = false;
        for (Task task : user.getTasks()){
            if(task.getName() != null && !task.getUsers().isEmpty()){
                taskHasContents = true;
                break;
            }
        }
        assertTrue(taskHasContents);

        // Assert
        //checking same for projects:
        assertNotNull(user.getProjects());
        assertFalse(user.getProjects().isEmpty());

        //cross referencing the user's projects field values and the project's assigned user:
        boolean hasRelevantProject = false;
        for (Project p : user.getProjects()) {
            String name = p.getName();
            if (name != null && (name.contains("Website") || name.contains("Mobile"))) {
                hasRelevantProject = true;
                break;
            }
        }
        assertTrue(hasRelevantProject);
    }




}
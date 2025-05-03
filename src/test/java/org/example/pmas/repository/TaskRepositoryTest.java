package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)
class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Test
    void readAll_with_values() {
        // Arrange
        int expectedSize = 3;

        // Act
        var actualSize = taskRepository.readAll();

        // Assert
        assertEquals(expectedSize, actualSize.size());
    }
    @Sql(
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:h2NoData.sql"}
    )
    @Test
    void readAll_without_values() {
        // Arrange
        var expected = 0;

        // Act
        var actual = taskRepository.readAll();

        // Assert
        assertEquals(expected, actual.size());
    }

    @Test
    void readSelected_with_data(){
        // Arrange
        var expected = MockDataModel.taskWithValue();

        // Act
        var actual = taskRepository.readSelected(1);

        // Assert
        assertEquals(actual, expected);
    }
    @Sql(
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:h2NoData.sql"}
    )
    @Test
    void readSelected_without_data(){
        // Arrange

        // Act
        var actual = taskRepository.readSelected(5);

        // Assert
        assertNull(actual);
    }

    @Test
    void create_with_value(){
        // Arrange
        var task = new Task("test","test",
                40,
                0.0,
                false,
                LocalDate.of(2021, 1, 1),
                new SubProject(1, "UI Overhaul"),
                Set.of(
                        new User(1, "Rebecca Black"),
                        new User(2, "John Smith")));

        // Act
        var actual = taskRepository.create(task);
        var expected = taskRepository.readSelected(task.getId());

        // Assert
        assertEquals(expected, actual);
    }
}
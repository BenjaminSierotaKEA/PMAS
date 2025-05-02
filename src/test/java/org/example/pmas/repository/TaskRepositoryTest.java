package org.example.pmas.repository;

import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// runs this script before every method
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)

@Transactional
// rolls the database back after a run
@Rollback
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {

    }

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
        var result = taskRepository.readSelected(1);

        // Assert
        assertEquals(expected, result);
    }
    @Sql(
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:h2NoData.sql"}
    )
    @Test
    void readSelected_without_data(){
        // Arrange

        // Act
        var result = taskRepository.readSelected(5);

        // Assert
        assertNull(result);
    }
}
package org.example.pmas.repository;

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
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts ={"classpath:h2init.sql"}
)
@Transactional
@Rollback
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void readAll() {
        // Arrange
        int expectedSize = 3;

        // Act
        var actualSize = taskRepository.readAll();

        // Assert
        assertEquals(expectedSize, actualSize.size());
    }
}
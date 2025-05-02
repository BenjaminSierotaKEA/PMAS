package org.example.pmas.service;

import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
    }

    @Test
    void readAll_with_values() {
        // Arrange
        when(taskRepository.readAll()).thenReturn(tasks);

        // Act
        var result = taskService.readAll();

        // Assert
        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository).readAll();
    }

    @Test
    void readAll_without_values() {
        // Arrange
        when(taskRepository.readAll()).thenReturn(null);

        // Act
        var result = taskService.readAll();

        // Assert
        assertNull(result);
        verify(taskRepository).readAll();
    }
}
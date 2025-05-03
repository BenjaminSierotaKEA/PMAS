package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private List<Task> tasks;
    private Task task;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
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

    @Test
    void readSelected_with_values() {
        // Arrange
        when(taskRepository.readSelected(1)).thenReturn(task);

        // Act
        var result = taskService.readSelected(1);

        // Assert
        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository).readSelected(1);
    }
    @Test
    void readSelected_without_values() {
        // Arrange
        when(taskRepository.readSelected(1)).thenReturn(null);

        // Act
        // Executable is an interface and i need to override that method.
        Executable executable = new Executable() {
            @Override
            public void execute() {
                // executes this task
                taskService.readSelected(1);
            }
        };

        // Assert
        var result = assertThrows(WrongInputException.class, executable);
        assertEquals("Task blev ikke fundet", result.getMessage());
        verify(taskRepository).readSelected(1);
    }

    @Test
    void create_with_values(){
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(task);

        // Act
        boolean expected = taskService.create(task);

        // Assert
        verify(taskRepository).create(task);
        assertTrue(expected);
    }
    @Test
    void create_without_values(){
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(null);

        // Act
        boolean expected = taskService.create(task);

        // assert
        verify(taskRepository).create(task);
        assertFalse(expected);
    }
}
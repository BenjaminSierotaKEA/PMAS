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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private List<Task> tasks;
    private Task task;
    List<Integer> userIDs;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
        userIDs = new ArrayList<>(List.of(1,2,3));
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
        verify(taskRepository, times(1)).readAll();
    }
    @Test
    void readAll_without_values() {
        // Arrange
        when(taskRepository.readAll()).thenReturn(null);

        // Act
        var result = taskService.readAll();

        // Assert
        assertNull(result);
        verify(taskRepository, times(1)).readAll();
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
        verify(taskRepository, times(1)).readSelected(1);
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
        assertEquals("Der er noget galt med id.", result.getMessage());
        verify(taskRepository, times(1)).readSelected(1);
    }

    @Test
    void create_with_values(){
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(task);


        // Act
        boolean expected = taskService.create(task,userIDs);

        // Assert
        verify(taskRepository, times(1)).create(task);
        assertTrue(expected);
    }
    @Test
    void create_without_values(){
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(null);

        // Act
        boolean expected = taskService.create(task,userIDs);

        // assert
        verify(taskRepository, times(1)).create(task);
        assertFalse(expected);
    }

    @Test
    void delete_with_value(){
        // Arrange
        when(taskRepository.delete(1)).thenReturn(true);
        when(taskRepository.readSelected(1)).thenReturn(task);

        // Act
        taskService.delete(1);

        // Assert
        verify(taskRepository, times(1)).delete(1);
        verify(taskRepository, times(1)).readSelected(1);
    }
    @Test
    void delete_without_value(){
        // Arrange
        int taskId = 1;
        when(taskRepository.readSelected(taskId)).thenReturn(null);

        // Act
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.delete(1);
            }
        };

        // Assert
        var result = assertThrows(WrongInputException.class, executable);
        assertEquals("Der noget galt med id.", result.getMessage());
        verify(taskRepository, times(1)).readSelected(taskId);
        verify(taskRepository, never()).delete(anyInt());
    }

    @Test
    void update_with_values() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.readSelected(1)).thenReturn(task);
        when(taskRepository.update(task)).thenReturn(true);
        when(taskRepository.addUserToTask(1, List.of(2, 3))).thenReturn(true);

        boolean result = taskService.update(task, List.of(2, 3));

        assertTrue(result);

        verify(taskRepository).readSelected(1);
        verify(taskRepository).update(task);
        verify(taskRepository).addUserToTask(1, List.of(2, 3));
    }
    @Test
    void update_return_false() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        when(taskRepository.readSelected(1)).thenReturn(task);
        when(taskRepository.update(task)).thenReturn(false);

        // Act
        boolean result = taskService.update(task, List.of(2, 3));

        // Assert
        assertFalse(result);
        verify(taskRepository).readSelected(1);
        verify(taskRepository).update(task);
        verifyNoMoreInteractions(taskRepository);
    }
    @Test
    void update_should_throw() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        when(taskRepository.readSelected(1)).thenReturn(null);

        // Act
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.update(task, List.of(2, 3));
            }
        };

        // Assert
        assertThrows(WrongInputException.class, executable);
        verify(taskRepository).readSelected(1);
        verifyNoMoreInteractions(taskRepository);
    }
}
package org.example.pmas.service;

import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private ISubProjectRepository subProjectRepository;

    @InjectMocks
    private TaskService taskService;

    private List<Task> tasks;
    private Task task;
    Set<Integer> userIDs;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
        userIDs = new HashSet<>(List.of(1, 2, 3));
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
    void create_with_values() {
        // Arrange
        when(subProjectRepository.doesSubProjectExist(1)).thenReturn(true);
        when(taskRepository.create(task)).thenReturn(task);

        // Act
        taskService.create(task, userIDs);

        // Assert
        verify(taskRepository, times(1)).create(task);
    }

    @Test
    void delete_with_value() {
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
    void update_with_values() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        when(taskRepository.readSelected(1)).thenReturn(task);
        when(taskRepository.update(task)).thenReturn(true);

        // Act
        taskService.update(task, Set.of(2, 3));

        // Assert
        verify(taskRepository).readSelected(1);
        verify(taskRepository).update(task);
    }
}
package org.example.pmas.service;

import org.example.pmas.exception.NotFoundException;
import org.example.pmas.exception.UpdateObjectException;
import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.TaskRepository;
import org.example.pmas.service.comparators.TaskDeadlineComparator;
import org.example.pmas.service.comparators.TaskPriorityComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        userIDs = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    void readAll_with_values() {
        // Arrange
        when(taskRepository.readAll()).thenReturn(tasks);
        List<Task> expected = new ArrayList<>(tasks);
        expected.sort(
                new TaskDeadlineComparator().reversed()
                        .thenComparing(new TaskPriorityComparator())
        );

        // Act
        var actual = taskService.readAll();

        // Assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(taskRepository, times(1)).readAll();
    }


    @Test
    void readAll_without_values() {
        // Arrange
        when(taskRepository.readAll()).thenReturn(null);
        List<Task> actual = new ArrayList<>();

        // Act
        var expected = taskService.readAll();

        // Assert
        assertEquals(actual,expected);
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
        assertThrows(NotFoundException.class, executable);
        verify(taskRepository, times(1)).readSelected(1);
    }

    @Test
    void create_with_values() {
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(task);

        // Act
        taskService.create(task, userIDs);

        // Assert
        verify(taskRepository, times(1)).create(task);
    }

    @Test
    void create_without_values() {
        // Arrange
        when(taskRepository.create(any(Task.class))).thenReturn(null);

        // Act
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.create(task, userIDs);
            }
        };
        // assert
        assertThrows(NotFoundException.class, executable);
        verify(taskRepository, times(1)).create(task);
        verifyNoMoreInteractions(taskRepository);
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
    void delete_notvalid_id() {
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
        var result = assertThrows(NotFoundException.class, executable);
        assertEquals(null, result.getMessage());
        verify(taskRepository, times(1)).readSelected(taskId);
        verify(taskRepository, never()).delete(anyInt());
    }

    @Test
    void update_with_values() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        when(taskRepository.readSelected(1)).thenReturn(task);
        when(taskRepository.update(task)).thenReturn(true);

        // Act
        taskService.update(task, List.of(2, 3));

        // Assert
        verify(taskRepository).readSelected(1);
        verify(taskRepository).update(task);
    }

    @Test
    void update_throw_when_update_returns_false() {
        // Arrange
        Task task = new Task();
        task.setId(1);

        when(taskRepository.readSelected(1)).thenReturn(task);
        when(taskRepository.update(task)).thenReturn(false);

        // Act & Assert
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.update(task, List.of(2, 3));
            }
        };

        assertThrows(UpdateObjectException.class, executable);
        verify(taskRepository).readSelected(1);
        verify(taskRepository).update(task);
        verifyNoMoreInteractions(taskRepository);
    }


    @Test
    void update_should_throw_after_readSelected() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        when(taskRepository.readSelected(1)).thenReturn(null);

        // Act
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.readSelected(1);
            }
        };

        // Assert
        assertThrows(NotFoundException.class, executable);
        verify(taskRepository).readSelected(1);
        verifyNoMoreInteractions(taskRepository);
    }
}
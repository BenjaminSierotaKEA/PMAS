package org.example.pmas.controller;

import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.TaskService;
import org.example.pmas.util.SessionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private List<Task> tasks;
    private Task task;
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private SessionHandler sessionHandler;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
    }

    @Test
    void readAllTaskBySubProjectID() throws Exception {
        // Arrange
        when(sessionHandler.isNotAdmin()).thenReturn(true);
        when(sessionHandler.isUserProjectManager()).thenReturn(true);
        when(taskService.getTasksBySubProjectID(1))
                .thenReturn(tasks);

        // Act & Assert
        mvc.perform(get("/projects/{projectId}/subprojects/{subprojectID}/tasks/all", 1, 1))
                .andExpect(status().isOk())
                .andExpect(view().name("task-all"))
                .andExpect(model().attributeExists("allowAccess"))
                .andExpect(model().attributeExists("ProjectManager"))
                .andExpect(model().attributeExists("tasks"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(taskService).getTasksBySubProjectID(1);
    }

    @Test
    void readSelected() throws Exception {
        // Arrange
        when(taskService.readSelected(any(Integer.class)))
                .thenReturn(task);
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        User user = MockDataModel.userWithValues();

        when(sessionHandler.getCurrentUser()).thenReturn(user);

        // Act & Assert
        mvc.perform(get("/projects/{projectId}/subprojects/{subprojectID}/tasks/{id}/edit", 1,1,1)
                        .sessionAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("task-update"))
                .andExpect(model().attributeExists("projectId"))
                .andExpect(model().attributeExists("subprojectId"))
                .andExpect(model().attributeExists("allowAccess"))
                .andExpect(model().attributeExists("task"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(taskService).readSelected(any(Integer.class));
    }

    @Test
    void createTask() throws Exception {
        // Arrange
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        // Act & Assert
        mvc.perform(post("/projects/{projectId}/subprojects/{subprojectID}/tasks/create", 1,1)
                        .param("id", String.valueOf(1))
                        .param("name", task.getName())
                        .param("description", task.getDescription())
                        .param("timeBudget", String.valueOf(task.getTimeBudget()))
                        .param("completed", String.valueOf(task.isCompleted()))
                        .param("deadline", String.valueOf(task.getDeadline()))
                        .param("subProject.id", "1")
                        .param("userIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/subprojects/1/tasks/all"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(taskService, times(1))
                .create(any(Task.class), any(Set.class));
    }

    @Test
    void deleteTask() throws Exception {
        // Arrange
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        // Act & Assert
        mvc.perform(post("/projects/{projectId}/subprojects/{subprojectID}/tasks/{id}/delete", 1,1,1)
                        .param("id", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/subprojects/1/tasks/all"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(taskService, times(1))
                .delete(any(Integer.class));
    }

    @Test
    void updateTask() throws Exception {
        // Arrange
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        // Act & Assert
        mvc.perform(post("/projects/{projectId}/subprojects/{subprojectID}/tasks/update",1,1)
                        .param("id", String.valueOf(1))
                        .param("name", task.getName())
                        .param("description", task.getDescription())
                        .param("timeBudget", String.valueOf(task.getTimeBudget()))
                        .param("completed", String.valueOf(task.isCompleted()))
                        .param("subProject.id", "1")
                        .param("userIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/subprojects/1/tasks/all"));

        verify(taskService, times(1))
                .update(any(Task.class), any(Set.class));
        verify(sessionHandler, times(1)).isNotAdmin();
    }
}
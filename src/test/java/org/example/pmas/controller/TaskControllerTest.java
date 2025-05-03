package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
    }

    @Test
    void readAll() throws Exception {
        // Arrange
        when(taskService.readAll()).thenReturn(tasks);

        // Act & Assert
        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-all"))
                .andExpect(model().attributeExists("tasks"));

        verify(taskService).readAll();
    }

    @Test
    void readSelected() throws Exception {
        // Arrange
        when(taskService.readSelected(any(Integer.class))).thenReturn(task);

        // Act & Assert
        mvc.perform(get("/tasks/{id}/task", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("task-selected"))
                .andExpect(model().attributeExists("task"));

        verify(taskService).readSelected(any(Integer.class));
    }

    @Test
    void getCreateTaskPage() throws Exception {
        // Arrange
        List<SubProject> subProjects = List.of(
                new SubProject(1, "UI Overhaul"),
                new SubProject(2, "Backend Overhaul"));
        when(taskService.getAllSubproject()).thenReturn(subProjects);

        // Act & Assert
        mvc.perform(get("/tasks/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("subprojects"))
                .andExpect(view().name("task-new"));

        verify(taskService).getAllSubproject();
    }
}
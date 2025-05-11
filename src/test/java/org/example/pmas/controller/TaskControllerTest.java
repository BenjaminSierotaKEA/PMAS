package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private List<Task> tasks;
    private Task task;
    private SubProject subproject;
    private  Project dummyProject = new Project();

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private SubProjectService subProjectService;

    @MockitoBean
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
        task = MockDataModel.taskWithValue();
        var temp = MockDataModel.subprojectsWithValues();
        subproject = temp.getFirst();
        dummyProject.setId(1);

        when(subProjectService.readSelected(1)).thenReturn(subproject);
        when(projectService.readSelected(1)).thenReturn(dummyProject);
    }

    @Test
    void readAllTaskBySubProjectID() throws Exception {
        // Arrange
        when(taskService.getTasksBySubProjectID(1)).thenReturn(tasks);

        // Act & Assert
        mvc.perform(get("/projects/{projectId}/subprojects/{subprojectID}/tasks/all", 1, 1))
                .andExpect(status().isOk())
                .andExpect(view().name("task-all"))
                .andExpect(model().attributeExists("tasks"));

        verify(taskService).getTasksBySubProjectID(1);
    }

    @Test
    void readSelected() throws Exception {
        // Arrange
        when(taskService.readSelected(any(Integer.class))).thenReturn(task);

        // Act & Assert
        mvc.perform(get("/projects/{projectId}/subprojects/{subprojectID}/tasks/{id}/edit", 1,1,1))
                .andExpect(status().isOk())
                .andExpect(view().name("task-update"))
                .andExpect(model().attributeExists("task"));

        verify(taskService).readSelected(any(Integer.class));
    }

    @Test
    void createTask() throws Exception {
        // Arrange

        // Act & Assert
        mvc.perform(post("/tasks/create")
                        .param("id", String.valueOf(1))
                        .param("name", task.getName())
                        .param("description", task.getDescription())
                        .param("timeBudget", String.valueOf(task.getTimeBudget()))
                        .param("timeTaken", String.valueOf(task.getTimeTaken()))
                        .param("completed", String.valueOf(task.isCompleted()))
                        .param("deadline", String.valueOf(task.getDeadline()))
                        .param("subProject.id", "1")
                        .param("userIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1))
                .create(any(Task.class), any(List.class));
    }

    @Test
    void deleteTask() throws Exception {
        // Arrange

        // Act & Assert
        mvc.perform(post("/tasks/{id}/delete", 1)
                        .param("id", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1))
                .delete(any(Integer.class));
    }

    @Test
    void updateTask() throws Exception {
        // Arrange

        // Act & Assert
        mvc.perform(post("/tasks/update")
                        .param("id", String.valueOf(1))
                        .param("name", task.getName())
                        .param("description", task.getDescription())
                        .param("timeBudget", String.valueOf(task.getTimeBudget()))
                        .param("timeTaken", String.valueOf(task.getTimeTaken()))
                        .param("completed", String.valueOf(task.isCompleted()))
                        .param("subProject.id", "1")
                        .param("userIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1))
                .update(any(Task.class), any(List.class));
    }
}
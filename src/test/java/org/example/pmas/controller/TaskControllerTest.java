package org.example.pmas.controller;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TaskControllerTest {

    private List<Task> tasks;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        tasks = MockDataModel.tasksWithValues();
    }

    @Test
    void readAll() throws Exception {
        when(taskService.readAll()).thenReturn(tasks);

        mvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(view().name("task"))
                .andExpect(model().attributeExists("tasks"));

        verify(taskService).readAll();
    }
}
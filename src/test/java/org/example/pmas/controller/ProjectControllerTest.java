package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.Role;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.service.TaskService;
import org.example.pmas.util.SessionHandler;
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

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    private Project project;
    private List<Project> projects;


    @Autowired
    private MockMvc mvc;


    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private SessionHandler sessionHandler;


    @BeforeEach
    void setup(){
        project = MockDataModel.projectWithValues();
    }


    @Test
    void getCreateProjectForm() {
        //Arrange
        //Act
        //Assert
    }

    //WIP
    /*
    @Test
    void createProject() throws Exception {
        //Arrange
        //when the inserted function is called, it will be mocked to return true:
        when(sessionHandler.isUserProjectManager()).thenReturn(true);

        //Act
        mvc.perform(post("/projects/create", 1,1)
                .param("id", String.valueOf(1))
                .param("name", project.getName())
                .param("description", project.getDescription())
                .param("timeBudget", String.valueOf(project.getTimeBudget()))
                .param("deadline", String.valueOf(project.getDeadline())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/my-projects"));
        //Assert
        verify(sessionHandler, times(1)).isNotAdmin();
        verify(projectService, times(1))
                .createProject(any(Project.class));
    }
 */
    @Test
    void seeAll() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void myProjects() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void updateForm() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void updateProject() {
        //Arrange
        //Act
        //assert
    }

    @Test
    void deleteProject() {
        //Arrange
        //Act
        //Assert
    }

}
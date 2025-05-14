package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.util.SessionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubProjectController.class)
public class SubProjectControllerTest {

    private List<SubProject> subprojects;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SubProjectService subprojectService;

    @MockitoBean
    private SessionHandler sessionHandler;

    @BeforeEach
    void setUp() {
        subprojects = MockDataModel.subprojectsWithValues();
    }

//    @Test
//    void shouldReturnAllSubProjects() throws Exception {
//        when(subprojectService.getSubProjectDTOByProjectId(2)).thenReturn(subprojects);
//
//        mvc.perform(get("/projects/2/subprojects/all"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("subprojects-all"))
//                .andExpect(model().attributeExists("subprojects"));
//
//        verify(subprojectService).getSubProjectDTOByProjectId(2);
//    }

//    @Test
//    void shouldReturnSelectedSubProject() throws Exception {
//        SubProject subproject = subprojects.getFirst();
//        when(subprojectService.readSelected(1)).thenReturn(subproject);
//
//        mvc.perform(get("/projects/1/subprojects/selected"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("subproject-selected"))
//                .andExpect(model().attributeExists("subproject"))
//                .andExpect(model().attribute("subproject",subproject));
//
//        verify(subprojectService).readSelected(1);
//    }


    @Test
    void createSubProject_shouldRenderCreateSubProjectForm() throws Exception {
        int projectID = 1;
        Project mockProject = new Project();
        mockProject.setId(projectID);
        when(sessionHandler.isNotAdmin()).thenReturn(true);
        when(subprojectService.getProjectById(projectID)).thenReturn(mockProject);

        mvc.perform(get("/projects/{projectId}/subprojects/new", projectID))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject-new"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("allowAccess"))
                .andExpect(model().attributeExists("subproject"));

        verify(sessionHandler, times(1)).isNotAdmin();
    }

    //needs to be updated to redirect to projectlist when projectlist is added
    @Test
    void createSubProject_shouldRedirectToProjectList() throws Exception {
        int projetId = 1;
        SubProject newSubProject = new SubProject(4,"SubProject4","SubProject4Desc");
        when(subprojectService.create(newSubProject)).thenReturn(newSubProject);
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        mvc.perform(post("/projects/{projectId}/subprojects/create", projetId)
                .param("id", "123")
                .param("name", "Updated Name")
                .param("description", "Updated Description")
                .param("timeBudget", "10.0")
                .param("timeTaken", "5.0")
                .param("completed", "true")
                .param("projectID", String.valueOf(projetId))
                .flashAttr("subproject", new SubProject()))
                .andExpect(redirectedUrl("/projects/"+projetId+"/subprojects/all"))
                .andExpect(status().is3xxRedirection());

        verify(sessionHandler,times(1)).isNotAdmin();
        verify(subprojectService,times(1)).create(any(SubProject.class));
    }

    @Test
    void delete_shouldRedirectToProjectList() throws Exception {
        SubProject subproject = subprojects.getFirst();
        int idToDelete = subproject.getId();
        int projectID = subproject.getProjectID();
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        mvc.perform(post("/projects/{projectId}/subprojects/{subprojectID}/delete",projectID,idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/0/subprojects/all"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(subprojectService).delete(idToDelete);
    }

    @Test
    void editSubProject_shouldReturnEditSubProjectForm() throws Exception {
        SubProject subproject = subprojects.getFirst();
        when(subprojectService.readSelected(1)).thenReturn(subproject);
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        mvc.perform(get("/projects/{projectId}/subprojects/{subprojectId}/edit",subproject.getProjectID(),subproject.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject-edit-form"))
                .andExpect(model().attributeExists("allowAccess"))
                .andExpect(model().attributeExists("subproject"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(subprojectService, times(1)).readSelected(1);
    }

    @Test
    void updateSubProject_shouldRedirectBackToSubProject() throws Exception {
        int projectId = 1;
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        mvc.perform(post("/projects/{projectId}/subprojects/update", projectId)
                        .param("id", "123")
                        .param("name", "Updated Name")
                        .param("description", "Updated Description")
                        .param("timeBudget", "10.0")
                        .param("timeTaken", "5.0")
                        .param("completed", "true")
                        .param("projectID", String.valueOf(projectId))
                        .flashAttr("subproject", new SubProject()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/subprojects/all"));

        verify(sessionHandler, times(1)).isNotAdmin();
        verify(subprojectService, times(1)).updateSubProject(any(SubProject.class));
    }
}

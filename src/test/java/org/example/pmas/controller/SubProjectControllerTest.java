package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.SubProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubProjectController.class)
public class SubProjectControllerTest {

    private List<SubProject> subprojects;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SubProjectService subprojectService;

    @BeforeEach
    void setUp() {
        subprojects = MockDataModel.subprojectsWithValues();
    }

    @Test
    void shouldReturnAllSubProjects() throws Exception {
        when(subprojectService.readAll()).thenReturn(subprojects);

        mvc.perform(get("/subprojects"))
                .andExpect(status().isOk())
                .andExpect(view().name("subprojects-all"))
                .andExpect(model().attributeExists("subprojects"));

        verify(subprojectService).readAll();
    }

    @Test
    void shouldReturnSelectedSubProject() throws Exception {
        SubProject subproject = subprojects.getFirst();
        when(subprojectService.readSelected(1)).thenReturn(subproject);

        mvc.perform(get("/subprojects/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject-selected"))
                .andExpect(model().attributeExists("subproject"))
                .andExpect(model().attribute("subproject",subproject));

        verify(subprojectService).readSelected(1);
    }

    @Test
    void createSubProject_shouldRenderCreateSubProjectForm() throws Exception {
        int projectID = 1;

        mvc.perform(get("/projects/{projectID}/subprojects/create", projectID))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject-new"))
                .andExpect(model().attributeExists("subproject"));
    }

    //needs to be updated to redirect to projectlist when projectlist is added
    @Test
    void createSubProject_shouldRedirectToProjectList() throws Exception {
        SubProject newSubProject = new SubProject(4,"SubProject4","SubProject4Desc");

        when(subprojectService.create(newSubProject)).thenReturn(newSubProject);

        mvc.perform(post("/projects/save")
                        .param("projectID","4")
                        .flashAttr("subproject", newSubProject))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/4/subprojects"));
        verify(subprojectService).create(newSubProject);
    }

    //needs to be updated to redirect to projectlist when projectlist is added
    @Test
    void delete_shouldRedirectToProjectList() throws Exception {
        SubProject subproject = subprojects.getFirst();
        int idToDelete = subproject.getId();
        //int projectID = subproject.getProjectID();

        mvc.perform(post("/projects/{subprojectID}/delete",idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(subprojectService).delete(idToDelete);
    }

    @Test
    void editSubProject_shouldReturnEditSubProjectForm() throws Exception {
        SubProject subproject = subprojects.getFirst();
        when(subprojectService.readSelected(1)).thenReturn(subproject);

        mvc.perform(get("/subprojects/{subprojectID}/edit",subproject.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject-edit-form"))
                .andExpect(model().attributeExists("subproject"));
    }

    @Test
    void updateSubProject_shouldRedirectBackToSubProject() throws Exception {
        SubProject subproject = subprojects.getFirst();

        mvc.perform(post("/subprojects/update").flashAttr("subproject", subproject))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subprojects/" + subproject.getId()));

        verify(subprojectService).updateSubProject(subproject);
    }
}

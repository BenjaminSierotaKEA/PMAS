package org.example.pmas;

import org.example.pmas.model.SubProject;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.util.SessionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts ={"classpath:h2init.sql"}
)

@Transactional
@Rollback
public class SubProjectIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    SubProjectService subprojectService;

    //Have to mock for the test to pass due to sessionHandler.isNotAdmin() call
    @MockitoBean
    SessionHandler sessionHandler;

    @Test
    public void createSubProjectShouldPersistToDatabase() throws Exception {
        when(sessionHandler.isNotAdmin()).thenReturn(true);

        mvc.perform(post("/projects/1/subprojects/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "IntegrationTestName")
                        .param("description", "IntegrationTestDescription")
                        .param("timeBudget", "1.1")
                        .param("timeTaken", "0")
                        .param("completed", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/subprojects/all"));

        List<SubProject> projects = subprojectService.readAll();

        boolean matchFound = false;
        for (SubProject p : projects) {
            if ("IntegrationTestName".equals(p.getName())
                    && "IntegrationTestDescription".equals(p.getDescription())
                    && p.getProjectID() == 1) {
                matchFound = true;
                break;
            }
        }

        assertTrue(matchFound);
    }

}

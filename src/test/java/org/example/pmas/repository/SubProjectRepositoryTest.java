package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts ={"classpath:h2init.sql"}
)
@Transactional
@Rollback
public class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Test
    public void shouldReturnAllSubProjects() {
        int expectedValue = 3;

        int actual = subProjectRepository.readAll().size();

        assertEquals(expectedValue, actual);
    }

    @Test
    public void shouldReturnASpecificSubProjectById() {
        SubProject subproject = subProjectRepository.readSelected(1);

        assertNotNull(subproject);
        assertEquals("UI Overhaul", subproject.getName());
        assertEquals(1,subproject.getId());
    }

    @Test
    public void shouldDeleteASubProjectById() {
        int sizeBefore = subProjectRepository.readAll().size();
        subProjectRepository.delete(1);
        int sizeAfter = subProjectRepository.readAll().size();

        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void shouldCreateASubProject() {
        int sizeBefore = subProjectRepository.readAll().size();
        subProjectRepository.create(new SubProject("UI Overhauls", "Update the UI/UX of the website.", 200.0, false, 1));
        int sizeAfter = subProjectRepository.readAll().size();

        assertEquals(sizeBefore + 1, sizeAfter);
    }
}

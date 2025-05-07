package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldReturnAllSubProjects() {
        //Use Integer(Object) to check if a null value is returned. Even though jdbc.query returns an empty list
        //if null, a null value can be given in the rowmapper if NOT NULL.
        Integer expected = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM subprojects", Integer.class);
        int expectedValue = (expected == null) ? 0 : expected;

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
        subProjectRepository.create(new SubProject("UI Overhauls", "Update the UI/UX of the website.", 200, 200, false, 1));
        int sizeAfter = subProjectRepository.readAll().size();

        assertEquals(sizeBefore + 1, sizeAfter);
    }
}

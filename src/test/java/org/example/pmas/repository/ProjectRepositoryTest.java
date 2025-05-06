package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.example.pmas.model.rowMapper.ProjectRowMapper;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)

@Transactional
@Rollback

class ProjectRepositoryTest {


    @Autowired
    private ProjectRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
        //Populating the database with stuff
        String sql = "INSERT INTO projects (name, description, timeBudget, deadline)" +
                " VALUES ('Website Redesign', 'Redesigning the company website.', 500, '2021-07-14'), " +
                "('Mobile App', 'Developing the new company mobile app.', 800, '2022-03-09')";

    }

    @Test
    void create() {
        //Arrange
        Project testProject = new Project(-1,"Test Project", " A project used for the test", 1, LocalDate.now());
        String checkSql = "SELECT * FROM projects WHERE projects.name='Test Project'";

        //Act
        repository.create(testProject);

        //Assert
        List<Project> projectsFound = jdbcTemplate.query(checkSql,new ProjectRowMapper());

        assertEquals(projectsFound.size(), 1);
        Project foundProject = projectsFound.get(0);
        assertEquals(foundProject.getName(), testProject.getName());
        assertEquals(foundProject.getDescription(), testProject.getDescription());
        assertEquals(foundProject.getTimeBudget(), testProject.getTimeBudget());
        assertEquals(foundProject.getDeadline(), testProject.getDeadline());

    }

    @Test
    void readAll() {
    }

    @Test
    void readSelected() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void doesProjectExist() {
    }


}
package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.example.pmas.model.rowMapper.ProjectRowMapper;
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
import java.util.ArrayList;
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
        //jdbcTemplate.update(sql);

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

        assertEquals(1, projectsFound.size());
        Project foundProject = projectsFound.get(0);
        assertEquals(foundProject.getName(), testProject.getName());
        assertEquals(foundProject.getDescription(), testProject.getDescription());
        assertEquals(foundProject.getTimeBudget(), testProject.getTimeBudget());
        assertEquals(foundProject.getDeadline(), testProject.getDeadline());

    }

    @Test
    void readAll() {

        //Arrange
        List<Project> expectedProjects = new ArrayList<>();
        expectedProjects.add(new Project(1, "Website Redesign", "Redesigning the company website.", 500, LocalDate.of(2021, 7, 14)));
        expectedProjects.add(new Project(2, "Mobile App", "Developing the new company mobile app.", 800, LocalDate.of(2022, 3, 9) ));
        //Act
        List<Project> foundProjects = repository.readAll();
        //Asssert
        assertEquals(expectedProjects.size(), foundProjects.size());
        for(int i = 0; i < expectedProjects.size(); i++){
            Project expectedProject = expectedProjects.get(i);
            Project foundProject = foundProjects.get(i);

            assertEquals(expectedProject.getId(), foundProject.getId());
            assertEquals(expectedProject.getName(), foundProject.getName());
            assertEquals(expectedProject.getDescription(), foundProject.getDescription());
            assertEquals(expectedProject.getTimeBudget(), foundProject.getTimeBudget());
            assertEquals(expectedProject.getDeadline(), foundProject.getDeadline());

        }
    }

    @Test
    void readSelected() {
        //Arrange
        Project expectedProject = new Project(1, "Website Redesign", "Redesigning the company website.", 500, LocalDate.of(2021, 7, 14));
        //Act
        Project foundProject = repository.readSelected(1);
        //Assert
        assertEquals(expectedProject.getId(), foundProject.getId());
        assertEquals(expectedProject.getName(), foundProject.getName());
        assertEquals(expectedProject.getDescription(), foundProject.getDescription());
        assertEquals(expectedProject.getTimeBudget(), foundProject.getTimeBudget());
        assertEquals(expectedProject.getDeadline(), foundProject.getDeadline());
    }

    @Test
    void delete() {
        //Arrange
        Project testProject = new Project(-1, "Deletion test project", "lets test", 100, LocalDate.now());
        repository.create(testProject);
        int totalProjects = repository.readAll().size();

        //Act
        repository.delete(3);

        //Assert
        int currentTotalProjects = repository.readAll().size();
        assertEquals(totalProjects -1, currentTotalProjects);
    }

    @Test
    void update() {

        //Arrange
        LocalDate now = LocalDate.now();
        Project updateProject = repository.readSelected(1);
        updateProject.setName("update test");
        updateProject.setDescription("update test");
        updateProject.setTimeBudget(69420);
        updateProject.setDeadline(now);
        //Act
        repository.update(updateProject);
        Project foundProject = repository.readSelected(1);
        //Assert
        assertEquals(updateProject, foundProject);

    }

    @Test
    void doesProjectExist() {
        //Arrange
        //Act
        boolean result = repository.doesProjectExist(1);
        //Assert
        assertEquals(true, result);
    }


}
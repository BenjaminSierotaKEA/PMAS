package org.example.pmas.modelBuilder;

import org.example.pmas.model.*;
import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.model.Role;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.model.enums.PriorityLevel;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// This class can be used to mock data for testing purposes.
public class MockDataModel {

    public static Task taskWithValue(){
        return new Task(
                1,
                "Create Mockups",
                "Create mockup screens for new website design.",
                PriorityLevel.LOW,
                40.0,
                false,
                LocalDate.of(2023, 11, 17),
                new SubProject(1, "UI Overhaul"),
                Set.of(new User(1, "Rebecca Black"))
        );
    }

    public static List<Task> tasksWithValues() {
        return List.of(
                new Task(
                        1,
                        "Create Mockups",
                        "Create mockup screens for new website design.",
                        PriorityLevel.LOW,
                        40.0,
                        false,
                        LocalDate.of(2023, 11, 17),
                        new SubProject(1, "UI Overhaul"),
                        Set.of(new User(1, "Rebecca Black"))
                ),
                new Task(
                        2,
                        "Implement Login API",
                        "Develop authentication endpoints.",
                        PriorityLevel.MEDIUM,
                        60.0,
                        false,
                        LocalDate.of(2021, 4, 5),
                        new SubProject(2, "Backend API"),
                        Set.of(new User(2, "John Smith"))
                ),
                new Task(
                        3,
                        "Build Profile Screen",
                        "Create profile page design for app.",
                        PriorityLevel.HIGH,
                        50.0,
                        false,
                        LocalDate.of(2024, 8, 29),
                        new SubProject(3, "App UI Design"),
                        Set.of(new User(3, "CharlieXcX"))
                )
        );
    }

    public static User userWithValues(){
        return new User(1,"jacob", "email", "password", new Role(), "jacob.jpg");
    }

    public static List<User> usersWithValues(){
        return List.of(
                new User(1,"jacob", "email", "password", new Role(1, "Admin"), "jacob.jpg" ),
                new User(2,"Peter", "email", "password", new Role(1, "Admin"), "peter.jpg" ),
                new User(3,"Sune", "email", "password", new Role(1, "Admin"), "sune.jpg" )
        );
    }

    public static List<SubProject> subprojectsWithValues(){
        return List.of(
                new SubProject(1,"SubProject1", "SubProject1Desc"),
                new SubProject(2,"SubProject2", "SubProject2Desc"),
                new SubProject(3,"SubProject3", "SubProject3Desc")
        );
    }


    public static List<SubProjectDTO> subprojectsDTOWithValues() {
        return List.of(
                new SubProjectDTO(1, "UI Overhaul", "Update the UI/UX of the website.", 1),
                new SubProjectDTO(2, "Backend API", "Develop new APIs for the app.", 2)
        );
    }

    public static Project projectWithValues() {
        Project p = new Project();
        p.setId(2);
        p.setName("Mobile App");
        p.setDescription("Developing the new company mobile app.");
        p.setTimeBudget(800);
        p.setDeadline(LocalDate.of(2022, 3, 9));
        return p;
    }



}

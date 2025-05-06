package org.example.pmas.modelBuilder;

import org.example.pmas.model.Role;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class can be used if the same model is used in multiple classes
public class MockDataModel {

    public static Task taskWithValue(){
        return new Task(1,"Create Mockups","Create mockup screens for new website design.",
                40.0,0.0,
                false,
                LocalDate.now(),
                new SubProject(1, "UI Overhaul"),
                Set.of(new User(1, "Rebecca Black")));
    }

    public static List<Task> tasksWithValues() {
        return List.of(new Task(1,
                        "Amalie",
                        "Lav noget",
                        5.5,
                        4.0,
                        true,
                        LocalDate.now(),
                        new SubProject(),
                        new HashSet<>()),
                new Task(2,
                        "Niklas",
                        "Mere noget",
                        5.5,
                        0.0,
                        false,
                        LocalDate.now(),
                        new SubProject(),
                        new HashSet<>()
                ));
    }

    public static User userWithValues(){
        return new User(1,"jacob", "email", "password", new Role(), "jacob.jpg");
    }

    public static List<User> usersWithValues(){
        return List.of(
                new User(1,"jacob", "email", "password", new Role(), "jacob.jpg" ),
                new User(1,"Peter", "email", "password", new Role(), "peter.jpg" ),
                new User(1,"Sune", "email", "password", new Role(), "sune.jpg" )
        );
    }

    public static List<SubProject> subprojectsWithValues(){
        return List.of(
                new SubProject(1,"SubProject1", "SubProject1Desc"),
                new SubProject(2,"SubProject2", "SubProject2Desc"),
                new SubProject(3,"SubProject3", "SubProject3Desc")
        );
    }



}

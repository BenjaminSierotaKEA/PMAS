package org.example.pmas.modelBuilder;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class can be used if the same model is used in multiple classes
public class MockDataModel {

    public static Task taskWithValue(){
        return new Task(1,"Create Mockups","Create mockup screens for new website design.",
                40,0.0,
                false,
                new SubProject(1, "UI Overhaul"),
                Set.of(new User(1, "Rebecca Black")));
    }

    public static List<Task> tasksWithValues() {
        return List.of(new Task(1,
                        "Amalie",
                        "Lav noget",
                        5.5,
                        4,
                        true,
                        new SubProject(),
                        new HashSet<>()),
                new Task(2,
                        "Niklas",
                        "Mere noget",
                        5.5,
                        0.0,
                        false,
                        new SubProject(),
                        new HashSet<>()
                ));
    };
}

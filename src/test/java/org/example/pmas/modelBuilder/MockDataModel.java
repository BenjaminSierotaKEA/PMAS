package org.example.pmas.modelBuilder;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;

import java.util.HashSet;
import java.util.List;

// This class can be used if the same model is used in multiple classes
public class MockDataModel {

    public static Task taskWithValue(){
        return new Task(1,"test","et test object",
                10.0,5.0,
                true,new SubProject(), new HashSet<>());
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

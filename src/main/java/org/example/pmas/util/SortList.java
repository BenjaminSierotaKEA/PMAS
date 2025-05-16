package org.example.pmas.util;

import org.example.pmas.model.Project;
import org.example.pmas.model.Task;
import org.example.pmas.model.dto.ProjectDTO;
import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.service.comparators.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class is meant to hold our sorting. If needed, we can use sort across different services.
public class SortList {


    // Sorts the list by deadline and then priority.
    // If the list is null, return an empty list. No errors.
    public static List<Task> tasksDeadlinePriority(List<Task> taskList) {
        // If the list is null, return an empty list. No errors
        if (taskList == null) return Collections.emptyList();

        // Sort the list by deadline and then priority.
        // We copy the list, so it's not immutable
        List<Task> modifiableList = new ArrayList<>(taskList);
        modifiableList.sort(new TaskDeadlineComparator().reversed()
                .thenComparing(new TaskPriorityComparator())
        );
        return modifiableList;
    }

    // Sorts the list by deadline.
    // If the list is null, return an empty list. No errors.
    public static List<Project> projectsDeadline(List<Project> projects){
        // If the list is null, return an empty list. No errors
        if(projects == null) return Collections.emptyList();

        // Sort the list by deadline.
        // We copy the list, so it's not immutable
        List<Project> modifiableList = new ArrayList<>(projects);
        modifiableList.sort(new ProjectDeadlineComparator().reversed());

        return modifiableList;
    }

    // Sorts the list by deadline.
    // If the list is null, return an empty list. No errors.
    public static List<ProjectDTO> projectsDTODeadline(List<ProjectDTO> projects){
        // If the list is null, return an empty list. No errors
        if(projects == null) return Collections.emptyList();

        // Sort the list by deadline
        // We copy the list, so it's not immutable
        List<ProjectDTO> modifiableList = new ArrayList<>(projects);
        modifiableList.sort(new ProjectDTODeadlineComparator().reversed());

        return modifiableList;
    }

    // Sorts the list by name.
    // If the list is null, return an empty list. No errors.
    public static List<SubProjectDTO> subProjectDTOName(List<SubProjectDTO> subprojects){
        // If the list is null, return an empty list. No errors
        if(subprojects == null) return Collections.emptyList();
        // We sort the list on name
        // We copy the list, so it's not immutable
        List<SubProjectDTO> modifiableList = new ArrayList<>(subprojects);
        modifiableList.sort(new SubProjectNameComparator());

        return modifiableList;
    }
}

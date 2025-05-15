package org.example.pmas.util;

import org.example.pmas.model.Task;
import org.example.pmas.service.comparators.TaskDeadlineComparator;
import org.example.pmas.service.comparators.TaskPriorityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTaskList {


    // Sorts the list by deadline and then priority.
    // If the list is null, return an empty list. No errors
    public static List<Task> sortList(List<Task> taskList) {
        // If the list is null, return an empty list. No errors
        if (taskList == null) return Collections.emptyList();

        // Sort the list by deadline and then priority.
        // We copy the list so its not immutable
        List<Task> modifiableList = new ArrayList<>(taskList);
        modifiableList.sort(new TaskDeadlineComparator().reversed()
                .thenComparing(new TaskPriorityComparator())
        );
        return modifiableList;
    }
}

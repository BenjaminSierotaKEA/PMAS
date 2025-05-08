package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;

import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // if both don't have a value
        if (o1.getPriorityLevel() == null && o2.getPriorityLevel() == null) {
            return 0;
            // o1 doesn't have a value
        } else if (o1.getPriorityLevel() == null) {
            return 1;
            // o2 doesn't have a value
        } else if (o2.getPriorityLevel() == null) {
            return -1;
            // Sorts priority by a -> z
        } else {
            return o1.getPriorityLevel().compareTo(o2.getPriorityLevel());
        }
    }
}

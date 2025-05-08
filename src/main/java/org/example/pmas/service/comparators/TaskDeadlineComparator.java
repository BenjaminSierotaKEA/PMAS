package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;

import java.util.Comparator;

public class TaskDeadlineComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // if both don't have a value
        if (o1.getDeadline() == null && o2.getDeadline() == null) {
            return 0;
            // o1 doesn't have a value
        } else if (o1.getDeadline() == null) {
            return 1;
            // o2 doesn't have a value
        } else if (o2.getDeadline() == null) {
            return -1;
            // Sorts dates from high -> low
        } else {
            return o1.getDeadline().compareTo(o2.getDeadline());
        }
    }
}

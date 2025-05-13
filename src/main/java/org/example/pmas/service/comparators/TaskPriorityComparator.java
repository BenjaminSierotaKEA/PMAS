package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;
import org.example.pmas.model.enums.PriorityLevel;

import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // get priority level for o1 and o2
        PriorityLevel priority1 = o1.getPriorityLevel();
        PriorityLevel priority2 = o2.getPriorityLevel();

        // if both have null values, put last
        if (priority1 == null && priority2 == null) return 0;
        // priority1 is null put last
        if (priority1 == null) return 1;
        // if priority2 is null, put after priority1
        if (priority2 == null) return -1;

        // if both have a value, compare them
        return Integer.compare(priority1.getSortOrder(), priority2.getSortOrder());
    }
}

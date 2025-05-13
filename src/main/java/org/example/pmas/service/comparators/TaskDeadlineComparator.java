package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;
import org.example.pmas.model.enums.PriorityLevel;

import java.time.LocalDate;
import java.util.Comparator;

public class TaskDeadlineComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // get deadline for o1 and o2
        LocalDate date1 = o1.getDeadline();
        LocalDate date2 = o2.getDeadline();

        // if both dates are null, put last
        if (date1 == null && date2 == null) return 0;
        // if date1 is null, put last
        if (date1 == null) return 1;
        // if date2 is null, put last
        if (date2 == null) return -1;

        // if both dates are not null, compare them.
        return date1.compareTo(date2);
    }
}

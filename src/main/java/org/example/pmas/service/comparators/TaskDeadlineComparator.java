package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;

import java.time.LocalDate;
import java.util.Comparator;

public class TaskDeadlineComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // get deadline for o1 and o2
        LocalDate date1 = o1.getDeadline();
        LocalDate date2 = o2.getDeadline();

        var compare = new CompareAttributes();
        return compare.localDate(date1, date2);
    }
}

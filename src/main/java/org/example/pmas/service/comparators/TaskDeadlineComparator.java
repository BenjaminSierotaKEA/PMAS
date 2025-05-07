package org.example.pmas.service.comparators;

import org.example.pmas.model.Task;

import java.util.Comparator;

public class TaskDeadlineComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        // Begge har ikke deadline. Behold deres plads i rækkefølgen
        if (o1.getDeadline() == null && o2.getDeadline() == null) {
            return 0;
            // o1 har ingen deadline, skal længere ned på listen
        } else if (o1.getDeadline() == null) {
            return 1;
            // o2 har ingen deadline, skal længere ned på listen
        } else if (o2.getDeadline() == null) {
            return -1;
            // Begge har deadlines, sorter stigende efter deadline
        } else {
            return o1.getDeadline().compareTo(o2.getDeadline());
        }
    }
}

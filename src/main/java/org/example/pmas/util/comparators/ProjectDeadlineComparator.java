package org.example.pmas.util.comparators;

import org.example.pmas.model.Project;

import java.time.LocalDate;
import java.util.Comparator;

public class ProjectDeadlineComparator implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        // get deadline for o1 and o2
        LocalDate date1 = o1.getDeadline();
        LocalDate date2 = o2.getDeadline();

        var compare = new CompareAttributes();
        return compare.localDate(date1, date2);
    }
}

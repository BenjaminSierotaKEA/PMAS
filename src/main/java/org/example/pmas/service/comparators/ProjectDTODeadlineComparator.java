package org.example.pmas.service.comparators;

import org.example.pmas.model.dto.ProjectDTO;

import java.time.LocalDate;
import java.util.Comparator;

public class ProjectDTODeadlineComparator implements Comparator<ProjectDTO> {
    @Override
    public int compare(ProjectDTO o1, ProjectDTO o2) {
        // get deadline for o1 and o2
        LocalDate date1 = o1.getDeadline();
        LocalDate date2 = o2.getDeadline();

        return checkLocalDate.checkDate(date1, date2);
    }
}

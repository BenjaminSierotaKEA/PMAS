package org.example.pmas.util;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.dto.SubProjectDTO;

import java.util.List;

public class CompletionStatCalculator{

    public CompletionStatCalculator() {

    }

    public void calculateTaskCompletionPercentage(List<SubProjectDTO> list) {
        for(SubProjectDTO dto : list) {
            if (dto.getTotalTasks() == 0) {
                dto.setCompletionPercentage(0);
            } else {
                double completionPercentage = 1.0 * dto.getCompletedTasks() * 100 / dto.getTotalTasks();
                dto.setCompletionPercentage(completionPercentage);
            }
            checkIfSubProjectCompleted(dto);
        }
    }

    public void checkIfSubProjectCompleted(SubProjectDTO dto) {
        if(dto.getTimeTaken() == dto.getTimeBudget()) {
            dto.setCompleted(true);
        }
    }

    public void calculateSubProjectCompletionPercentage(List<ProjectDTO> list) {
        for(ProjectDTO dto : list) {
            if (dto.getTotalSubProjects() == 0) {
                dto.setCompletionPercentage(0);
            } else {
                double completionPercentage = 1.0 * dto.getCompletedSubProjects() * 100 / dto.getTotalSubProjects();
                dto.setCompletionPercentage(completionPercentage);
            }
            checkIfProjectCompleted(dto);
        }
    }

    public void checkIfProjectCompleted(ProjectDTO dto) {
        if(dto.getTimeTaken() == dto.getTimeBudget()) {
            dto.setCompleted(true);
        }
    }
}

package org.example.pmas.util;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.dto.SubProjectDTO;

import java.util.List;

public class CompletionStatCalculator{

    public CompletionStatCalculator() {
    }

    public static void calculateTaskCompletionPercentage(List<SubProjectDTO> list) {
        for (SubProjectDTO dto : list) {
            dto.setCompletionPercentage(calculatePercentage(dto.getCompletedTasks(), dto.getTotalTasks()));
            checkIfSubProjectCompleted(dto);
        }
    }

    private static double calculatePercentage(int completed, int total) {
        return total == 0 ? 0 : (completed * 100.0) / total;
    }

    public static void checkIfSubProjectCompleted(SubProjectDTO dto) {
        if(dto.getTimeTaken() == dto.getTimeBudget()) {
            dto.setCompleted(true);
        }
    }

    public static void calculateSubProjectCompletionPercentage(List<ProjectDTO> list) {
        for (ProjectDTO dto : list) {
            dto.setCompletionPercentage(calculatePercentage(dto.getCompletedSubProjects(), dto.getTotalSubProjects()));
            checkIfProjectCompleted(dto);
        }
    }

    public static void checkIfProjectCompleted(ProjectDTO dto) {
        if(dto.getTimeTaken() == dto.getTimeBudget()) {
            dto.setCompleted(true);
        }
    }
}

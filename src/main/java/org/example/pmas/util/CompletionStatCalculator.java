package org.example.pmas.util;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.dto.SubProjectDTO;

import java.util.List;

public class CompletionStatCalculator{

    public CompletionStatCalculator() {
    }

    public static double calculatePercentage(int completed, int total) {
        return total == 0 ? 0 : (completed * 100.0) / total;
    }

    public static boolean isJobCompleted(double timeTaken, double timeBudget) {
        return Double.compare(timeTaken, timeBudget) == 0;
    }
}

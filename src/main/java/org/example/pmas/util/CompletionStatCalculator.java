package org.example.pmas.util;

public class CompletionStatCalculator{

    public CompletionStatCalculator() {
    }

    public static double calculatePercentage(int completed, int total) {
        return total == 0 ? 0 : (completed * 100.0) / total;
    }

    public static boolean isJobCompleted(int completed, int total) {
        return completed == total;
    }
}

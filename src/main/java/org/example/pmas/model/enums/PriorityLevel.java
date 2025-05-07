package org.example.pmas.model.enums;

public enum PriorityLevel {
    LOW("Lav"),
    MEDIUM("Middel"),
    HIGH("høj");

    private final String displayName;

    PriorityLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

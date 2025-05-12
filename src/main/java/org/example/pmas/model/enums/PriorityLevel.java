package org.example.pmas.model.enums;

public enum PriorityLevel {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String displayName;

    PriorityLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

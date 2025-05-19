package org.example.pmas.model.enums;

public enum PriorityLevel {
    // the display name is for UI. sortOrder is for Comparator: A value to sort on
    LOW("Low",3),
    MEDIUM("Medium",2),
    HIGH("High",1);

    private final String displayName;
    private final int sortOrder;

    PriorityLevel(String displayName, int sortOrder) {
        this.displayName = displayName;
        this.sortOrder = sortOrder;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}

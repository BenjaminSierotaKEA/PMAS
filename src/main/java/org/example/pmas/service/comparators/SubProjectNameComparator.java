package org.example.pmas.service.comparators;

import org.example.pmas.model.dto.SubProjectDTO;

import java.util.Comparator;

public class SubProjectNameComparator implements Comparator<SubProjectDTO> {
    @Override
    public int compare(SubProjectDTO o1, SubProjectDTO o2) {
        // It's casesensitive. 'A' and 'a' are compared different.
        return compareAttributes.string(o1.getName(), o2.getName());
    }
}

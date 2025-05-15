package org.example.pmas.service.comparators;

import org.example.pmas.dto.SubProjectDTO;

import java.util.Comparator;

public class SubProjectNameComparator implements Comparator<SubProjectDTO> {
    @Override
    public int compare(SubProjectDTO o1, SubProjectDTO o2) {
        // It's casesensitive. 'A' and 'a' are compared different.
        return o1.getName()
                .toLowerCase()
                .compareTo(o2.getName().toLowerCase());
    }
}

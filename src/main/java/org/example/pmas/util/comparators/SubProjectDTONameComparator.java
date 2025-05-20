package org.example.pmas.util.comparators;

import org.example.pmas.model.dto.SubProjectDTO;

import java.util.Comparator;

public class SubProjectDTONameComparator implements Comparator<SubProjectDTO> {
    @Override
    public int compare(SubProjectDTO o1, SubProjectDTO o2) {
        var compare = new CompareAttributes();
        return compare.string(o1.getName(), o2.getName());
    }
}

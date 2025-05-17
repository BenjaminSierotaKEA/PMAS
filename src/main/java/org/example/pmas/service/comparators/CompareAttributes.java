package org.example.pmas.service.comparators;

import java.time.LocalDate;

public class CompareAttributes {
    public int localDate(LocalDate date1, LocalDate date2){
        // if both dates are null, put last
        if (date1 == null && date2 == null) return 0;
        // if date1 is null, put last
        if (date1 == null) return 1;
        // if date2 is null, put last
        if (date2 == null) return -1;

        // if both dates are not null, compare them.
        return date1.compareTo(date2);
    }

    public int string(String name1, String name2){
        // It's casesensitive. 'A' and 'a' are compared differently.
        return name1
                .toLowerCase()
                .compareTo(name2.toLowerCase());
    }
}

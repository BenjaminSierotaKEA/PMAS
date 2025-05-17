package org.example.pmas.service.comparators;

import org.example.pmas.model.User;

import java.util.Comparator;

public class UserNameComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return compareAttributes.string(o1.getName(), o2.getName());
    }
}

package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Role;

import java.util.List;

public interface IRoleRepository {
    List<Role> readAll();
}

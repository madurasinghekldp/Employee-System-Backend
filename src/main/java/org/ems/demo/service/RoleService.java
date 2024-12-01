package org.ems.demo.service;

import org.ems.demo.dto.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    List<Role> getAllSelected(String l, String o);

    void updateRole(Role role);

    void deleteRole(Long id);

    List<Role> getAll();
}

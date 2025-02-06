package org.ems.demo.service;

import org.ems.demo.dto.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    List<Role> getAllSelected(Long companyId, String l, String o, String s);

    void updateRole(Role role);

    void deleteRole(Long id);

    List<Role> getAll(Long companyId);
}

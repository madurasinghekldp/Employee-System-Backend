package org.ems.demo.repository;

import org.ems.demo.entity.RoleEntity;

import java.util.List;

public interface RoleNativeRepository {
    List<RoleEntity> getAllSelected(Long companyId, String l, String o, String s);
}

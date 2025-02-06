package org.ems.demo.repository;

import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    Iterable<RoleEntity> findAllByCompany(CompanyEntity company);
}

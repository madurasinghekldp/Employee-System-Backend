package org.ems.demo.repository;


import org.ems.demo.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer> {
    Optional<UserRoleEntity> findByName(String name);
}

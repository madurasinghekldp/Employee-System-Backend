package org.ems.demo.repository;

import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {
    Optional<EmployeeEntity> findByUser(UserEntity userEntity);
}

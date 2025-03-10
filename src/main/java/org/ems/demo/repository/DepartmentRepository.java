package org.ems.demo.repository;

import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity,Long> {

    List<DepartmentEntity> findAllByCompany(CompanyEntity company);

}

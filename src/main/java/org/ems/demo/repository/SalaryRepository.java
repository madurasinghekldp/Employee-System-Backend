package org.ems.demo.repository;

import org.ems.demo.entity.SalaryEntity;
import org.springframework.data.repository.CrudRepository;

public interface SalaryRepository extends CrudRepository<SalaryEntity,Long> {
}

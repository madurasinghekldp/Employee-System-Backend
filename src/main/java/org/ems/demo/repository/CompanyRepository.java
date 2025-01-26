package org.ems.demo.repository;


import org.ems.demo.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<CompanyEntity,Long> {
}

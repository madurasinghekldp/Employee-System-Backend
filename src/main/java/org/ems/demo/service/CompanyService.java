package org.ems.demo.service;

import org.ems.demo.dto.Company;
import org.ems.demo.entity.CompanyEntity;

import java.util.Optional;

public interface CompanyService {

    CompanyEntity createCompany(Company company);
    Optional<CompanyEntity> getById(Company company);
}

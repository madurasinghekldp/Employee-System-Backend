package org.ems.demo.service;

import org.ems.demo.dto.Company;
import org.ems.demo.entity.CompanyEntity;

public interface CompanyService {

    CompanyEntity createCompany(Company company);
}

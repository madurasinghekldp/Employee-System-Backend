package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Company;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.CompanyRepository;
import org.ems.demo.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ObjectMapper mapper;
    @Override
    public CompanyEntity createCompany(Company company) {
        try{
            return companyRepository.save(mapper.convertValue(company, CompanyEntity.class));
        }
        catch(Exception e){
            throw new UserException("Company is not created!");
        }
    }
}

package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Company;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.exception.CompanyException;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.CompanyRepository;
import org.ems.demo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public Optional<CompanyEntity> getById(Company company){
        try{
            return companyRepository.findById(company.getId());
        }
        catch(Exception e){
            throw new UserException("Company is not found!");
        }
    }

    @Override
    public Company updateCompany(Company company) {
        try{
            Optional<CompanyEntity> existingCompany = companyRepository.findById(company.getId());
            if(existingCompany.isEmpty()) throw new CompanyException("Company is not found");
            CompanyEntity companyEntity = existingCompany.get();
            companyEntity.setName(company.getName());
            companyEntity.setAddress(company.getAddress());
            companyEntity.setRegisterNumber(company.getRegisterNumber());
            CompanyEntity saved = companyRepository.save(companyEntity);
            return mapper.convertValue(saved,Company.class);
        }
        catch(Exception e){
            throw new CompanyException("Company is not updated");
        }
    }
}

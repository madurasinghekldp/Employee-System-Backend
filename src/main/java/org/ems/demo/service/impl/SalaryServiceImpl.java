package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Salary;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.SalaryEntity;
import org.ems.demo.exception.SalaryException;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.SalaryNativeRepository;
import org.ems.demo.repository.SalaryRepository;
import org.ems.demo.service.SalaryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final SalaryNativeRepository salaryNativeRepository;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;

    @Override
    public Salary createSalary(Salary salary) {
        try{
            Optional<EmployeeEntity> employee = employeeRepository.findById(salary.getEmployee().getId());
            if(employee.isEmpty()) throw new SalaryException("Employee is not found!");
            SalaryEntity salaryEntity = mapper.convertValue(salary,SalaryEntity.class);
            salaryEntity.setEmployee(employee.get());
            SalaryEntity saved = salaryRepository.save(salaryEntity);
            return mapper.convertValue(saved, Salary.class);
        }
        catch(SalaryException e){
            throw e;
        }
        catch(Exception e){
            throw new SalaryException("Salary is not created!");
        }
    }

    @Override
    public List<Salary> getAllSalary(Long employeeId, int limit, int offset) {
        try{
            List<SalaryEntity> allSalaryByEmployee = salaryNativeRepository.getAllSalaryByEmployee(employeeId, limit, offset);
            if(allSalaryByEmployee.isEmpty()) throw new SalaryException("Salaries not found");
            List<Salary> salaryList = new ArrayList<>();
            allSalaryByEmployee.forEach(
                    salary->{
                        salaryList.add(mapper.convertValue(salary,Salary.class));
                    }
            );
            return salaryList;
        }
        catch(SalaryException e){
            throw e;
        }
        catch(Exception e){
            throw new SalaryException("Unknown error occurred");
        }
    }

    @Override
    public Salary updateSalary(Salary salary) {
        try{
            Optional<SalaryEntity> salaryById = salaryRepository.findById(salary.getId());
            if(salaryById.isEmpty()) throw new SalaryException("Salary is not found!");
            SalaryEntity salaryEntity = salaryById.get();
            salaryEntity.setPayment(salary.getPayment());
            salaryEntity.setPaymentDate(salary.getPaymentDate());
            return mapper.convertValue(salaryRepository.save(salaryEntity),Salary.class);
        }
        catch(SalaryException e){
            throw e;
        }
        catch(Exception e){
            throw new SalaryException("Salary is not Updated!");
        }
    }

    @Override
    public void deleteSalary(Long id) {
        try{
            Optional<SalaryEntity> byId = salaryRepository.findById(id);
            if(byId.isEmpty()) throw new SalaryException("Salary is not found!");
            salaryRepository.deleteById(id);
        }
        catch(SalaryException e){
            throw e;
        }
        catch(Exception e){
            throw new SalaryException("Salary is not deleted!");
        }
    }
}

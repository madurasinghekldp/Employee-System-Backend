package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Department;
import org.ems.demo.dto.Employee;
import org.ems.demo.dto.Role;
import org.ems.demo.entity.*;
import org.ems.demo.exception.EmployeeException;
import org.ems.demo.repository.CompanyRepository;
import org.ems.demo.repository.EmployeeNativeRepository;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeNativeRepository nativeRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    @Override
    public Employee create(Employee employee) {

        try{
            Optional<CompanyEntity> companyOp = companyRepository.findById(employee.getCompany().getId());
            if(companyOp.isEmpty()) throw new EmployeeException("Company is not found!");
            CompanyEntity company = companyOp.get();
            EmployeeEntity emp = mapper.convertValue(employee,EmployeeEntity.class);
            emp.setCompany(company);
            EmployeeEntity saved = repository.save(emp);
            return mapper.convertValue(saved,Employee.class);
        }
        catch(EmployeeException e){
            throw e;
        }
        catch(Exception e){
            throw new EmployeeException("Employee is not added!");
        }
    }

    @Override
    public List<Employee> getAllSelected(Long companyId,String l, String o, String s) {
        List<EmployeeEntity> selected = nativeRepository.getSelected(companyId,l,o,s);
        if(selected.isEmpty()) throw new EmployeeException("Employees are not found!");
        List<Employee> empList = new ArrayList<>();
        selected.forEach(emp->{
            Department department = mapper.convertValue(emp.getDepartment(), Department.class);
            Role role = mapper.convertValue(emp.getRole(), Role.class);
            Employee employee = mapper.convertValue(emp, Employee.class);
            employee.setDepartment(department);
            employee.setRole(role);
            employee.setFirstName(emp.getUser().getFirstName());
            employee.setLastName(emp.getUser().getLastName());
            employee.setEmail(emp.getUser().getEmail());
            empList.add(employee);
        });
        return empList;
    }

    @Transactional
    @Override
    public void updateEmp(Employee employee) {
        if(repository.existsById(employee.getId())){
            try{
                EmployeeEntity existingEmployee = repository.findById(employee.getId()).get();
                UserEntity userEntity = existingEmployee.getUser();
                userEntity.setFirstName(employee.getFirstName());
                userEntity.setLastName(employee.getLastName());
                userEntity.setEmail(employee.getEmail());
                UserEntity savedUser = userRepository.save(userEntity);
                existingEmployee.setUser(savedUser);
                existingEmployee.setRole(mapper.convertValue(employee.getRole(), RoleEntity.class));
                existingEmployee.setDepartment(mapper.convertValue(employee.getDepartment(), DepartmentEntity.class));
                repository.save(existingEmployee);
            }
            catch(Exception e){
                throw new EmployeeException("Employee is not updated!");
            }
        }
        else{
            throw new EmployeeException("Employee is not found!");
        }
    }

    @Transactional
    @Override
    public void deleteEmp(Long id) {
        if(repository.existsById(id)){
            EmployeeEntity employee = repository.findById(id).get();
            UserEntity user = employee.getUser();
            user.setActive(false);
            userRepository.save(user);
            repository.deleteById(id);
        }
        else{
            throw new EmployeeException("Employee is not found!");
        }
    }

    @Override
    public List<Employee> getAll(Long companyId, Long departmentId) {
        try{
            List<EmployeeEntity> all = nativeRepository.getAll(companyId,departmentId);
            List<Employee> empList = new ArrayList<>();
            all.forEach(
                    emp -> {
                        Employee employee = mapper.convertValue(emp, Employee.class);
                        employee.setFirstName(emp.getUser().getFirstName());
                        employee.setLastName(emp.getUser().getLastName());
                        empList.add(employee);
                    }
            );
            return empList;
        }
        catch(Exception e){
            throw new EmployeeException("No employees found!");
        }

    }

    @Override
    public Integer getCount(Long companyId) {
        return nativeRepository.getCount(companyId);
    }
}

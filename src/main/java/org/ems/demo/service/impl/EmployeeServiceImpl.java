package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Department;
import org.ems.demo.dto.Employee;
import org.ems.demo.dto.Role;
import org.ems.demo.entity.DepartmentEntity;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.RoleEntity;
import org.ems.demo.exception.EmployeeException;
import org.ems.demo.repository.EmployeeNativeRepository;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeNativeRepository nativeRepository;
    private final ObjectMapper mapper;

    @Override
    public Employee create(Employee employee) {

        try{
            EmployeeEntity saved = repository.save(mapper.convertValue(employee,EmployeeEntity.class));
            return mapper.convertValue(saved,Employee.class);
        }
        catch(Exception e){
            throw new EmployeeException("Employee is not added!");
        }
    }

    @Override
    public List<Employee> getAllSelected(String l, String o, String s) {
        List<EmployeeEntity> selected = nativeRepository.getSelected(l,o,s);
        if(selected.isEmpty()) throw new EmployeeException("Employees are not found!");
        List<Employee> empList = new ArrayList<>();
        selected.forEach(emp->{
            Department department = mapper.convertValue(emp.getDepartment(), Department.class);
            Role role = mapper.convertValue(emp.getRole(), Role.class);
            Employee employee = mapper.convertValue(emp, Employee.class);
            employee.setDepartment(department);
            employee.setRole(role);
            empList.add(employee);
        });
        return empList;
    }

    @Override
    public void updateEmp(Employee employee) {
        if(repository.existsById(employee.getId())){
            try{
                EmployeeEntity existingEmployee = repository.findById(employee.getId()).get();
                existingEmployee.setFirstName(employee.getFirstName());
                existingEmployee.setLastName(employee.getLastName());
                existingEmployee.setEmail(employee.getEmail());
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

    @Override
    public void deleteEmp(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new EmployeeException("Employee is not found!");
        }
    }
}

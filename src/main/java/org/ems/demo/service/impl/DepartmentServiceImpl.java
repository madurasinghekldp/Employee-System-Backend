package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Department;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.DepartmentEntity;
import org.ems.demo.exception.DepartmentException;
import org.ems.demo.repository.CompanyRepository;
import org.ems.demo.repository.DepartmentNativeRepository;
import org.ems.demo.repository.DepartmentRepository;
import org.ems.demo.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final ObjectMapper mapper;
    private final DepartmentNativeRepository nativeRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Department createDep(Department department) {

        try{
            DepartmentEntity saved = repository.save(mapper.convertValue(department, DepartmentEntity.class));
            return mapper.convertValue(saved, Department.class);
        }
        catch(Exception e){
            throw new DepartmentException("Department is not added!");
        }
    }

    @Override
    public List<Department> getAll(Long companyId) {
        Optional<CompanyEntity> company = companyRepository.findById(companyId);
        if(company.isEmpty()) throw new DepartmentException("Company is not found!");
        Iterable<DepartmentEntity> all = repository.findAllByCompany(company.get());
        if(((Collection<?>) all).isEmpty()) throw new DepartmentException("Departments are not found");
        List<Department> depList = new ArrayList<>();
        all.forEach(dep->{
            depList.add(mapper.convertValue(dep, Department.class));
        });
        return depList;
    }

    @Override
    public List<Department> getAllSelected(Long companyId, String l, String o, String s) {
        List<DepartmentEntity> selected = nativeRepository.getSelected(companyId,l, o, s);
        if(selected.isEmpty()) throw new DepartmentException("Departments are not found");
        List<Department> depList = new ArrayList<>();
        selected.forEach(dep->{
            depList.add(mapper.convertValue(dep, Department.class));
        });
        return depList;
    }

    @Override
    public void updateDep(Department department) {
        if(repository.existsById(department.getId())){
            try{
                DepartmentEntity existingDepartment = repository.findById(department.getId()).get();
                existingDepartment.setName(department.getName());
                existingDepartment.setDescription(department.getDescription());
                repository.save(existingDepartment);
            }
            catch(Exception e){
                throw new DepartmentException("Department is not updated!");
            }
        }
        else{
            throw new DepartmentException("Department is not found!");
        }
    }

    @Override
    public void deleteDep(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new DepartmentException("Department is not found!");
        }
    }
}

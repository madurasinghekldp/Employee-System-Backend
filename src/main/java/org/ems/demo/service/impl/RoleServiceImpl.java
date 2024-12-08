package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Role;
import org.ems.demo.entity.RoleEntity;
import org.ems.demo.exception.RoleException;
import org.ems.demo.repository.RoleNativeRepository;
import org.ems.demo.repository.RoleRepository;
import org.ems.demo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final ObjectMapper mapper;
    private final RoleRepository repository;
    private final RoleNativeRepository nativeRepository;

    @Override
    public Role createRole(Role role) {

        try{
            RoleEntity saved = repository.save(mapper.convertValue(role, RoleEntity.class));
            return mapper.convertValue(saved, Role.class);
        }
        catch(Exception e){
            throw new RoleException("Role is not added");
        }
    }

    @Override
    public List<Role> getAllSelected(String l, String o, String s) {
        List<RoleEntity> selected = nativeRepository.getAllSelected(l,o,s);
        if(selected.isEmpty()) throw new RoleException("Roles are not found");
        List<Role> roleList = new ArrayList<>();
        selected.forEach(role->{
            roleList.add(mapper.convertValue(role, Role.class));
        });
        return roleList;
    }

    @Override
    public void updateRole(Role role) {
        if(repository.existsById(role.getId())){
            try{
                RoleEntity existingRole = repository.findById(role.getId()).get();
                existingRole.setName(role.getName());
                existingRole.setDescription(role.getDescription());
                repository.save(existingRole);
            }
            catch(Exception e){
                throw new RoleException("Role is not updated!");
            }
        }
        else{
            throw new RoleException("Role is not found!");
        }
    }

    @Override
    public void deleteRole(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new RoleException("Role is not found!");
        }
    }

    @Override
    public List<Role> getAll() {
        Iterable<RoleEntity> all = repository.findAll();
        if(((Collection<?>) all).isEmpty()) throw new RoleException("Roles are not found");
        List<Role> roleList = new ArrayList<>();
        all.forEach(
                role->{
                    roleList.add(mapper.convertValue(role, Role.class));
                }
        );
        return roleList;
    }
}

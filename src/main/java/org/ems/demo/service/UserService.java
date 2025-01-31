package org.ems.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Company;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.User;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.entity.UserRoleEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserNativeRepository userNativeRepository;
    private final UserRoleRepository userRoleRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper;

    public UserService(
            UserRepository userRepository,
            ObjectMapper mapper,
            UserNativeRepository userNativeRepository,
            CompanyService companyService,
            UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userNativeRepository = userNativeRepository;
        this.companyService = companyService;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> allUsers() {
        List<UserEntity> userEntities = new ArrayList<>();

        userRepository.findAll().forEach(userEntities::add);

        return userEntities;
    }

    public User getUserByEmail(String email){
        UserEntity userByEmail = userNativeRepository.getUserByEmail(email);

        if(userByEmail==null){
            throw new UserException("User is not found!");
        }
        Company company = mapper.convertValue(userByEmail.getCompany(), Company.class);
        User mappeduser = mapper.convertValue(userByEmail, User.class);
        mappeduser.setCompany(company);
        return mappeduser;
    }

    public Object createUser(RegisterUserDto user) {
        log.info(user.toString());
        try{
            CompanyEntity company = companyService.getById(user.getCompany()).get();
            UserRoleEntity userRole = userRoleRepository.findByName(user.getUserRoleName()).get();
            UserEntity newUser = new UserEntity()
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .setUserRoleEntities(List.of(userRole))
                    .setCompany(company);
            return userRepository.save(newUser);
        }
        catch(Exception e){
            throw new UserException("User is not created!");
        }
    }
}

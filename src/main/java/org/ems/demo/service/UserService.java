package org.ems.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Company;
import org.ems.demo.dto.User;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.UserNativeRepository;
import org.ems.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserNativeRepository userNativeRepository;
    private final ObjectMapper mapper;

    public UserService(
            UserRepository userRepository,
            ObjectMapper mapper,
            UserNativeRepository userNativeRepository
    ) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userNativeRepository = userNativeRepository;
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
}

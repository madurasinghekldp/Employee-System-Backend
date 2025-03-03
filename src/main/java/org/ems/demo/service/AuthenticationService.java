package org.ems.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.LoginUserDto;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.entity.UserRoleEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.repository.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepository userRoleRepository;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;
    private final CompanyService companyService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserRoleRepository userRoleRepository,
            ObjectMapper mapper,
            CompanyService companyService,
            EmployeeRepository employeeRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
        this.companyService = companyService;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public UserEntity signup(RegisterUserDto input) {

        try{
            CompanyEntity company = companyService.createCompany(input.getCompany());
            Optional<UserEntity> user = userRepository.findByEmail(input.getEmail());
            if(user.isPresent()){
                throw new UserException("Email is already used!");
            }
            List<UserRoleEntity> userRoleList = new ArrayList<>();
            for (String name: input.getUserRoleName()) {
                userRoleList.add(userRoleRepository.findByName(name).get());
            }
            UserEntity userEntity = new UserEntity()
                    .setFirstName(input.getFirstName())
                    .setLastName(input.getLastName())
                    .setEmail(input.getEmail())
                    .setPassword(passwordEncoder.encode(input.getPassword()))
                    .setUserRoleEntities(userRoleList)
                    .setCompany(company);
            if(input.getUserRoleName().contains("ROLE_EMP")){
                EmployeeEntity employeeEntity = new EmployeeEntity()
                        .setUser(userEntity)
                        .setCompany(company);
                EmployeeEntity saved = employeeRepository.save(employeeEntity);
                return saved.getUser();
            }
            return userRepository.save(userEntity);
        }
        catch (UserException e){
            throw new UserException(e.getMessage());
        }
        catch(Exception e){
            throw new UserException("Signup process failed!");
        }
    }

    public UserEntity authenticate(LoginUserDto input) {
        try{
            Optional<UserEntity> user = userRepository.findByEmail(input.getEmail());
            if(user.isEmpty()){
                throw new UserException("You don't have an account!");
            }
            if(!user.get().isActive()) throw new UserException("Your account is deactivated!");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );

            return userRepository.findByEmail(input.getEmail())
                    .orElseThrow();
        }
        catch (UserException e){
            throw new UserException(e.getMessage());
        }
        catch(Exception e){
            throw new UserException("Login process failed!");
        }
    }
}

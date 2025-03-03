package org.ems.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.*;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.entity.UserRoleEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserNativeRepository userNativeRepository;
    private final UserRoleRepository userRoleRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final ObjectMapper mapper;

    public UserService(
            UserRepository userRepository,
            ObjectMapper mapper,
            UserNativeRepository userNativeRepository,
            CompanyService companyService,
            UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            EmployeeRepository employeeRepository
    ) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userNativeRepository = userNativeRepository;
        this.companyService = companyService;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.employeeRepository = employeeRepository;
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

    @Transactional
    public User createUser(RegisterUserDto user) {
        try{
            CompanyEntity company = companyService.getById(user.getCompany()).get();
            Optional<UserEntity> byEmail = userRepository.findByEmail(user.getEmail());
            List<UserRoleEntity> userRoleList = new ArrayList<>();
            for (String name: user.getUserRoleName()) {
                userRoleList.add(userRoleRepository.findByName(name).get());
            }

            UserEntity newUser = new UserEntity()
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .setUserRoleEntities(userRoleList)
                    .setCompany(company);


            if(user.getUserRoleName().contains("ROLE_EMP")){
                if(byEmail.isPresent()){
                    UserEntity userEntity = byEmail.get();
                    if(!userEntity.isActive()){
                        newUser.setId(userEntity.getId());
                        newUser.setActive(true);
                    }
                    else{
                        throw new UserException("User already exists.");
                    }
                }
                UserEntity savedUser = userRepository.save(newUser);
                Optional<EmployeeEntity> byUser = employeeRepository.findByUser(savedUser);
                if(byUser.isPresent()) throw new UserException("Employee already exists.");
                EmployeeEntity employeeEntity = new EmployeeEntity()
                        .setUser(savedUser)
                        .setCompany(company);
                employeeRepository.save(employeeEntity);
                emailService.sendSimpleMessage(newUser.getEmail(),
                        "Welcome to EMPGO employee management system.",
                        """
                                You can login to system using your email\s
                                and password as
                                """+user.getPassword()+ """
                            
                            Thank you,
                            empgo team.
                            """);
                return mapper.convertValue(savedUser,User.class);
            }
            if(byEmail.isPresent()){
                UserEntity userEntity = byEmail.get();
                if(!userEntity.isActive()){
                    newUser.setId(userEntity.getId());
                    newUser.setActive(true);
                }
                else{
                    throw new UserException("User already exists.");
                }
            }
            UserEntity saved = userRepository.save(newUser);
            emailService.sendSimpleMessage(newUser.getEmail(),
                    "Welcome to EMPGO employee management system.",
                    """
                            You can login to system using your email\s
                            and password as
                            """+user.getPassword()+ """
                            
                            Thank you,
                            empgo team.
                            """);
            return mapper.convertValue(saved, User.class);
        }
        catch(UserException e){
            throw e;
        }
        catch(Exception e){
            throw new UserException("User is not created!");
        }
    }

    public User updateUser(Integer id, UpdateUser user) {
        try{
            Optional<UserEntity> byId = userRepository.findById(id);
            if(byId.isEmpty()) throw new UserException("User not found!");
            UserEntity userEntity = byId.get();
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setEmail(user.getEmail());
            UserEntity saved = userRepository.save(userEntity);
            emailService.sendSimpleMessage(userEntity.getEmail(),
                    "Your profile details has been updated.",
                    """
                            Your profile details has been updated.
                            Please login back with your new Credentials.
                            
                            Thank you,
                            empgo team.
                            """
                    );
            return mapper.convertValue(saved, User.class);
        }
        catch(UserException e){
            throw e;
        }
        catch(Exception e){
            throw new UserException("User is not updated!");
        }
    }

    public void deleteUser(Integer id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if(byId.isEmpty()) throw new UserException("User not found!");
        UserEntity existingUser = byId.get();
        Optional<EmployeeEntity> byUser = employeeRepository.findByUser(existingUser);
        if(byUser.isPresent()){
            employeeRepository.deleteById(byUser.get().getId());
        }
        existingUser.setActive(false);
        userRepository.save(existingUser);
    }

    public Object updatePassword(Integer id, UpdatePassword updatePassword) {
        try{
            Optional<UserEntity> byId = userRepository.findById(id);
            if(byId.isEmpty()) throw new UserException("User not found!");
            UserEntity existingUser = byId.get();
            if(!passwordEncoder.matches(updatePassword.getOldPassword(), existingUser.getPassword())){
                throw new UserException("Incorrect old password!");
            }
            existingUser.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
            UserEntity saved = userRepository.save(existingUser);
            emailService.sendSimpleMessage(existingUser.getEmail(),
                    "Your password has been updated.",
                    """
                            Your password has been updated.
                            Contact us if this happened without knowingly.
                            
                            Thank you,
                            empgo team.
                            """
            );
            return mapper.convertValue(saved, User.class);
        }
        catch(UserException e){
            throw e;
        }
        catch(Exception e){
            throw new UserException("Password is not updated!");
        }
    }
}

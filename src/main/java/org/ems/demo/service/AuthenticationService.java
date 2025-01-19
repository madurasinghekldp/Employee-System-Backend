package org.ems.demo.service;

import org.ems.demo.dto.LoginUserDto;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.entity.UserRoleEntity;
import org.ems.demo.exception.UserException;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.repository.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserRoleRepository userRoleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserRoleRepository userRoleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public UserEntity signup(RegisterUserDto input) {

        try{
            Optional<UserEntity> user = userRepository.findByEmail(input.getEmail());
            if(user.isPresent()){
                throw new UserException("Email is already used!");
            }
            UserRoleEntity userRoleEntity = userRoleRepository.findByName(input.getUserRoleName())
                    .orElseThrow(() -> new RuntimeException("Default role not found"));

            UserEntity userEntity = new UserEntity()
                    .setFirstName(input.getFirstName())
                    .setLastName(input.getLastName())
                    .setEmail(input.getEmail())
                    .setPassword(passwordEncoder.encode(input.getPassword()))
                    .setUserRoleEntities(List.of(userRoleEntity));

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

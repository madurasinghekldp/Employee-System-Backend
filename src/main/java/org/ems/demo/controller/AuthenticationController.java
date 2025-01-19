package org.ems.demo.controller;


import org.ems.demo.dto.LoginResponse;
import org.ems.demo.dto.LoginUserDto;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.service.AuthenticationService;
import org.ems.demo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private String successStatus = "Success";

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registeredUserEntity = authenticationService.signup(registerUserDto);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(registeredUserEntity)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        UserEntity authenticatedUserEntity = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUserEntity);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(loginResponse)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}

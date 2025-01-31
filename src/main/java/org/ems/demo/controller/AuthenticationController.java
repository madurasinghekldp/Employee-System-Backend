package org.ems.demo.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.LoginResponse;
import org.ems.demo.dto.LoginUserDto;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.service.AuthenticationService;
import org.ems.demo.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final UserDetailsService userDetailsService;
    //private String successStatus = "Success";

    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService,
                                    UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registeredUserEntity = authenticationService.signup(registerUserDto);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(registeredUserEntity)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> authenticate(
            @RequestBody LoginUserDto loginUserDto,
            HttpServletResponse response
    ) {

        UserEntity authenticatedUserEntity = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUserEntity);

//        Cookie jwtCookie = new Cookie("token", jwtToken);
//        jwtCookie.setHttpOnly(false); // Prevent access to the cookie via JavaScript
//        jwtCookie.setSecure(true); // Ensure the cookie is sent only over HTTPS
//        jwtCookie.setPath("/"); // Set the cookie's path
//        jwtCookie.setMaxAge((int)jwtService.getExpirationTime()); // Set expiration time in seconds
//
//        // Add the cookie to the response
//        response.addCookie(jwtCookie);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(loginResponse)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}

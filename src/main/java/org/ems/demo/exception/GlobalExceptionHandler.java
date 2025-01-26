package org.ems.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String failedStatus = "Failed";

    @ExceptionHandler(DepartmentException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(DepartmentException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(RoleException.class)
    ResponseEntity<ErrorResponse> handleRoleException(RoleException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(EmployeeException.class)
    ResponseEntity<ErrorResponse> handleEmployeeException(EmployeeException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(UserException.class)
    ResponseEntity<ErrorResponse> handleUserException(UserException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    void handleAuthenticationException(AuthenticationException ex){
        log.info(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    void handleAccessDeniedException(AccessDeniedException ex){
        log.info(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    void handleAccessDeniedException(NoSuchElementException ex){
        log.info(ex.getMessage());
    }
}

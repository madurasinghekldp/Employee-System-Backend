package org.ems.demo.exception;

import org.ems.demo.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
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
}

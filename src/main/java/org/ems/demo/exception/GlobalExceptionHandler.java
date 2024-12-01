package org.ems.demo.exception;

import org.ems.demo.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String failedStatus = "Failed";

    @ExceptionHandler(DepartmentNotFoundException.class)
    ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }
}

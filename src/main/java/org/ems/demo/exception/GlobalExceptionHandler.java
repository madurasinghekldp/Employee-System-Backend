package org.ems.demo.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(LeaveException.class)
    ResponseEntity<ErrorResponse> handleLeaveException(LeaveException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(SalaryException.class)
    ResponseEntity<ErrorResponse> handleSalaryException(SalaryException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(TaskException.class)
    ResponseEntity<ErrorResponse> handleTaskException(TaskException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }


    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorResponse> handleAccountStatusException(Exception exception) {

        if (exception instanceof AccountStatusException) {
            //errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            //errorDetail.setProperty("description", "The account is locked");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(failedStatus)
                    .message("The account is locked")
                    .build();
            return ResponseEntity.ok().body(errorResponse);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message("Unknown error")
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception exception) {

        if (exception instanceof AccessDeniedException) {
            //errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            //errorDetail.setProperty("description", "You are not authorized to access this resource");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(failedStatus)
                    .message("You are not authorized to access this resource")
                    .build();
            return ResponseEntity.ok().body(errorResponse);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message("Unknown error")
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(Exception exception) {

        if (exception instanceof SignatureException) {
            //errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            //errorDetail.setProperty("description", "The JWT signature is invalid");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(failedStatus)
                    .message("The JWT signature is invalid")
                    .build();
            return ResponseEntity.ok().body(errorResponse);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(failedStatus)
                .message("Unknown error")
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception){
        ProblemDetail errorDetail = null;

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

        }
        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }
        log.info(errorDetail.toString());
        return errorDetail;
    }
}

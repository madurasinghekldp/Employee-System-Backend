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

//    @ExceptionHandler(AuthenticationException.class)
//    ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
//        log.info(ex.getMessage());
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
//        log.info(ex.getMessage());
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    ResponseEntity<ErrorResponse> handleAccessDeniedException(NoSuchElementException ex){
//        log.info(ex.getMessage());
//    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
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

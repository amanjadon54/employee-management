package com.ems.handler;

import com.ems.exception.ApiError;
import com.ems.exception.EmployeeManagementException;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.ems.constants.StringConstants.LOG_ID;
import static com.ems.constants.StringConstants.NAME_AGE_EXCEPTION;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleInvalidNameAgeException(
            ConstraintViolationException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getConstraintViolations().toString(),
                null);

        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidBeanExceptions(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, NAME_AGE_EXCEPTION,"");

        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(EmployeeManagementException.class)
    public ResponseEntity<ApiError> handleEmployeeManagementException(
            EmployeeManagementException e, HttpServletRequest request) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllException(
            Exception e, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
                e.getLocalizedMessage());

        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }


}

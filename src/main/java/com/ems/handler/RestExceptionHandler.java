package com.ems.handler;

import com.ems.exception.ApiError;
import com.ems.exception.EmployeeManagementException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.ems.constants.StringConstants.*;
import static com.ems.exception.record.ExceptionRecords.INVALID_EMPLOYEE_CREATE_ERROR_CODE;
import static com.ems.exception.record.ExceptionRecords.INVALID_NAME_ERROR_CODE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {


    Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleInvalidNameAgeException(
            ConstraintViolationException e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, NAME_AGE_EXCEPTION,
                INVALID_NAME_ERROR_CODE.getErrorCode());
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ApiError> handleNumberFormat(
            NumberFormatException e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, NUMBER_FORMAT_EXCEPTION,
                INVALID_NAME_ERROR_CODE.getErrorCode());
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidFormat(
            InvalidFormatException e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, NAME_AGE_EXCEPTION,
                INVALID_NAME_ERROR_CODE.getErrorCode());
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidBeanExceptions(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, CREATE_EMPLOYEE_EXCEPTION, INVALID_EMPLOYEE_CREATE_ERROR_CODE.getErrorCode());
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(EmployeeManagementException.class)
    public ResponseEntity<ApiError> handleEmployeeManagementException(
            EmployeeManagementException e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = e.getApiError();
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllException(
            Exception e, HttpServletRequest request) {
        log.error(e.toString());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
                e.getLocalizedMessage());

        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }


}

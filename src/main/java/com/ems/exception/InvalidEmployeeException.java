package com.ems.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidEmployeeException extends EmployeeManagementException {
    public InvalidEmployeeException(String message, String developerMessage) {
        super(new ApiError(HttpStatus.BAD_REQUEST, message, developerMessage));
    }
}

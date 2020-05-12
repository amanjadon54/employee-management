package com.ems.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DuplicateNameException extends EmployeeManagementException {
    public DuplicateNameException(String message, String developerMessage) {
        super(new ApiError(HttpStatus.PRECONDITION_FAILED, message, developerMessage));
    }
}

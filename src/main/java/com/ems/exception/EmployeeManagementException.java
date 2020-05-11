package com.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeManagementException extends RuntimeException {
    ApiError apiError;
}

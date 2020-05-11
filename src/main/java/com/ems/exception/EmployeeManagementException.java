package com.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeManagementException extends RuntimeException {
    private String message;
    private Integer status;
    private String developerMsg;
    private String logId;

    public EmployeeManagementException(String message, Integer status, String developerMsg) {
        this.message = message;
        this.status = status;
        this.developerMsg = developerMsg;
    }


}

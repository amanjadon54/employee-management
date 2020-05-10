package com.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomRuntimeException extends RuntimeException {
    private String message;
    private Integer status;
    private String developerMsg;
    private String logId;

    public CustomRuntimeException(String message, Integer status, String developerMsg) {
        this.message = message;
        this.status = status;
        this.developerMsg = developerMsg;
    }

}

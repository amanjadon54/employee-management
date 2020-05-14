package com.ems.exception;

import com.ems.exception.record.ExceptionRecords;
import lombok.Data;
import org.springframework.http.HttpStatus;

import static com.ems.constants.StringConstants.ERR_MESSAGE;

@Data
public class EmployeeNotFoundException extends EmployeeManagementException {

    public EmployeeNotFoundException(String message, String developerMessage) {
        super(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message,
                String.format(ERR_MESSAGE, ExceptionRecords.NO_RECORD_FOUND.getErrorCode(), developerMessage)));
    }
}

package com.ems.exception;

import com.ems.exception.record.ExceptionRecords;
import org.springframework.http.HttpStatus;

import static com.ems.constants.StringConstants.ERR_MESSAGE;

public class PayrollServiceException extends EmployeeManagementException {
    public PayrollServiceException(String message, String developerMessage) {
        super(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message,
                String.format(ERR_MESSAGE, ExceptionRecords.PAYROLL_SERVICE_ERROR_CODE.getErrorCode(), developerMessage)));
    }
}

package com.ems.exception.record;

public enum ExceptionRecords {
    INVALID_NAME_ERROR_CODE("ERROR_01"),
    INVALID_AGE_ERROR_CODE("ERROR_02"),
    PAYROLL_SERVICE_ERROR_CODE("ERROR_04"),
    NO_RECORD_FOUND("ERROR_05"),
    INVALID_EMPLOYEE_CREATE_ERROR_CODE("ERROR_06");

    private String errorCode;

    ExceptionRecords(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

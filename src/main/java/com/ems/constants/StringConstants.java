package com.ems.constants;

public class StringConstants {
    public static final String LOG_ID = "logId";
    public static final String methodName = "method_name";
    public static final String NAME_REGEX = "\\d?$";
    public static final String VALID_NAME_REGEX = "^[a-zA-Z]*$";
    public static final String VALID_SEARCH_NAME_REGEX = "^[a-zA-Z0-9]*$";
    public static final String NAME_AGE_EXCEPTION = "CONSTRAINT FAILED: name can only have alphabets, age should be in between 18 to 100, salary should be more than 1";
    public static final String CREATE_EMPLOYEE_EXCEPTION = NAME_AGE_EXCEPTION + ", salary should be more than 1 and should be numeric";
    public static final String NUMBER_FORMAT_EXCEPTION = "Expecting number instead of a string";
    public static final String ERR_MESSAGE = "ErrorCode : %s :: LogId:: %s";
}

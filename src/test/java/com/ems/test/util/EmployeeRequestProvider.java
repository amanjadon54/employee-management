package com.ems.test.util;

import com.ems.model.requests.CreateEmployeeRequest;

public class EmployeeRequestProvider {

    public static CreateEmployeeRequest createValidEmployee() {
        return new CreateEmployeeRequest("aman", 27, 2000);
    }

    public static CreateEmployeeRequest createInValidSalaryEmployee() {
        return new CreateEmployeeRequest("aman", 19, -1);
    }

    public static CreateEmployeeRequest createInValidAgeEmployee() {
        return new CreateEmployeeRequest("aman", 1, 2000);
    }

    public static CreateEmployeeRequest createInValidNameEmployee() {
        return new CreateEmployeeRequest("aman@@", 19, 2000);
    }

}

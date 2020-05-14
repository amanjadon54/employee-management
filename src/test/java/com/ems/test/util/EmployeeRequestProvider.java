package com.ems.test.util;

import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.EmployeeSalaryResponse;

import java.util.LinkedList;
import java.util.List;

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

    public static List<EmployeeSalaryResponse> prepareEmployeeSalaryList() {
        EmployeeSalaryResponse employeeSalaryResponse = new EmployeeSalaryResponse(1, "aman", 27, 20000);
        EmployeeSalaryResponse employeeSalaryResponse2 = new EmployeeSalaryResponse(2, "aman1", 27, 30000);
        List<EmployeeSalaryResponse> employeeSalaryResponseList = new LinkedList<>();
        employeeSalaryResponseList.add(employeeSalaryResponse);
        employeeSalaryResponseList.add(employeeSalaryResponse2);
        return employeeSalaryResponseList;
    }

}

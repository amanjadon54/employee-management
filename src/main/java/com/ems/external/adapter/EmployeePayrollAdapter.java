package com.ems.external.adapter;

import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployee;

public class EmployeePayrollAdapter {

    public static PayrollEmployee adaptEmployee(CreateEmployeeRequest employeeRequest) {
        return new PayrollEmployee(employeeRequest.getName(), String.valueOf(employeeRequest.getSalary()), String.valueOf(employeeRequest.getAge()));
    }
}

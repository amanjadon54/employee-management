package com.ems.external.adapter;


import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployee;
import org.springframework.stereotype.Component;

@Component
public class EmployeePayrollAdapter {

    public PayrollEmployee adaptEmployee(CreateEmployeeRequest employeeRequest) {
        return new PayrollEmployee(employeeRequest.getName(), String.valueOf(employeeRequest.getSalary()), String.valueOf(employeeRequest.getAge()));
    }
}

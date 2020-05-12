package com.ems.service;

import com.ems.annotation.MdcLog;
import com.ems.external.adapter.EmployeePayrollAdapter;
import com.ems.external.service.PayrollService;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManagerService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PayrollService payrollService;

    @Autowired
    EmployeePayrollAdapter adapter;

    @Autowired
    EmployeeJpaService employeeJpaService;

    @MdcLog
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        PayrollEmployeeResponse createdPayroll = payrollService.createPayroll(adapter.adaptEmployee(createEmployeeRequest));
        return employeeJpaService.createEmployee(createEmployeeRequest, createdPayroll.getPayrollEmployee().getId());
    }

    @MdcLog
    public List<Employee> fetchEmployeeByName(String name) {
        return employeeJpaService.fetchEmployeeByName(name);
    }

    @MdcLog
    public List<Employee> fetchEmployeeByAge(int age) {
        log.info("smple fetch emp by age called");
        //1. Find all the parollId on basis of partial serchby name
        //2. for each find the salary.
//       return  payrollService.fetchEmployeePayroll(age);
        return employeeJpaService.fetchEmployeeByAge(age);
    }
}

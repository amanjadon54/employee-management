package com.ems.service;

import com.ems.annotation.MdcLog;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PayrollService payrollService;

    @MdcLog
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        logger.info("create emp called with params");
        return null;
    }

    public List<Employee> fetchEmployeeByName(String name) {
        return null;
    }

    @MdcLog
    public PayrollEmployeeResponse fetchEmployeeByAge(int age) {
        logger.info("smple fetch emp by age called");
       return  payrollService.fetchEmployeePayroll(age);
    }
}

package com.ems.service;

import com.ems.annotation.MdcLog;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManagerService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @MdcLog
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        logger.info("create emp called with params");
        return null;
    }

    public List<Employee> fetchEmployeeByName(String name) {
        return null;
    }

    @MdcLog
    public List<Employee> fetchEmployeeByAge(int age) {
        logger.info("smple fetch emp by age called");
        return null;
    }
}

package com.ems.service;

import com.ems.annotation.MdcLog;
import com.ems.constants.StringConstants;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeJpaService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeRepository employeeRepository;

    @MdcLog
    public synchronized Employee createEmployee(CreateEmployeeRequest createEmployeeRequest, String payrollId) {
        Employee employee = new Employee();
        try {
            employee.setAge(createEmployeeRequest.getAge());
            employee.setName(createEmployeeRequest.getName());
            employee.setPayrollId(payrollId);
            employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error in saving the entity employee" + e);
            throw new EmployeeNotFoundException(e.getMessage(), "createEmployee failed::" + MDC.get(StringConstants.LOG_ID));
        }
        return employee;
    }

    @MdcLog
    public List<Employee> fetchEmployeeByName(String name) {
        return employeeRepository.findByNameContaining(name.toLowerCase());
    }

    @MdcLog
    public List<Employee> fetchEmployeeByAge(int age) {
        return employeeRepository.findByAge(age);
    }

    @MdcLog
    public List<String> fetchEmployeeByRegexNmae(String regexName) {
        return employeeRepository.findByName(regexName);
    }

    @MdcLog
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }
}
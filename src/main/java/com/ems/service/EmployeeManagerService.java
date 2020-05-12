package com.ems.service;

import com.ems.annotation.MdcLog;
import com.ems.exception.ApiError;
import com.ems.exception.DuplicateNameException;
import com.ems.external.adapter.EmployeePayrollAdapter;
import com.ems.external.service.PayrollService;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;
import com.ems.repository.EmployeeRepository;
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

    @Autowired
    EmployeePayrollAdapter adapter;

    @Autowired
    EmployeeRepository employeeRepository;

    @MdcLog
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        PayrollEmployeeResponse createdPayroll = payrollService.createPayroll(adapter.adaptEmployee(createEmployeeRequest));
        Employee employee = new Employee();
        try {
            employee.setAge(createEmployeeRequest.getAge());
            employee.setName(createEmployeeRequest.getName());
            employee.setPayrollId(createdPayroll.getPayrollEmployee().getId());
            employeeRepository.save(employee);
        } catch (Exception e) {
            logger.error("Error in saving the entity employee" + e);
            throw new DuplicateNameException(e.getMessage(), "createEmployee failed");
        }
        return employee;
    }

    public List<Employee> fetchEmployeeByName(String name) {
        return employeeRepository.findByNameContaining(name);
    }

    @MdcLog
    public List<Employee> fetchEmployeeByAge(int age) {
        logger.info("smple fetch emp by age called");
        //1. Find all the parollId on basis of partial serchby name
        //2. for each find the salary.
//       return  payrollService.fetchEmployeePayroll(age);
        return employeeRepository.findByAge(age);
    }
}

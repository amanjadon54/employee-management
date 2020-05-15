package com.ems.service;

import com.ems.annotation.MdcLog;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.external.adapter.EmployeePayrollAdapter;
import com.ems.external.service.PayrollService;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.EmployeeSalaryResponse;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.ems.constants.StringConstants.LOG_ID;
import static com.ems.constants.StringConstants.NAME_REGEX;

@Service
public class EmployeeManagerService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PayrollService payrollService;

    @Autowired
    EmployeeJpaService employeeJpaService;

    @MdcLog
    @Transactional
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        String name = getAvailableName(createEmployeeRequest.getName().toLowerCase());
        createEmployeeRequest.setName(name);
        PayrollEmployeeResponse createdPayroll = payrollService.createPayroll(EmployeePayrollAdapter.adaptEmployee(createEmployeeRequest));
        return employeeJpaService.createEmployee(createEmployeeRequest, createdPayroll.getPayrollEmployee().getId());
    }

    @MdcLog
    @Transactional
    public List<EmployeeSalaryResponse> fetchEmployeeByName(String name) {
        List<Employee> employees = employeeJpaService.fetchEmployeeByName(name);
        return prepareEmployeeSalary(employees);
    }

    @MdcLog
    @Transactional
    public List<EmployeeSalaryResponse> fetchEmployeeByAge(int age) {
        List<Employee> employees = employeeJpaService.fetchEmployeeByAge(age);
        return prepareEmployeeSalary(employees);
    }

    @MdcLog
    @Transactional
    public List<Employee> fetchAllEmployees() {
        return employeeJpaService.fetchAllEmployees();
    }

    private List<EmployeeSalaryResponse> prepareEmployeeSalary(List<Employee> employees) {
        if (employees != null && employees.size() >= 1) {
            List<EmployeeSalaryResponse> employeeSalaryResponse = new LinkedList<>();
            PayrollAllEmployeeResponse payrollDetails = payrollService.fetchEmployees();

            employees.forEach(employee -> {
                payrollDetails.getPayrollEmployee().stream().filter(payrollEmployee ->
                        employee.getPayrollId().equals(payrollEmployee.getId())).forEach(payrollEmployee -> {
                    EmployeeSalaryResponse salaryResponse = new EmployeeSalaryResponse(employee.getId(),
                            employee.getName(), employee.getAge(), Long.parseLong(payrollEmployee.getSalary()));
                    employeeSalaryResponse.add(salaryResponse);
                });

            });
            
            return employeeSalaryResponse;
        } else {
            throw new EmployeeNotFoundException("No Employee found", MDC.get(LOG_ID));
        }
    }

    private String getAvailableName(String name) {
        String nameRegex = name + NAME_REGEX;
        List<String> names = employeeJpaService.fetchEmployeeByRegexNmae(nameRegex);
        if (names != null && names.size() >= 1) {
            return name + names.size();
        }
        return name.toLowerCase();
    }
}

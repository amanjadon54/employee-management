package com.ems.service;

import com.ems.annotation.MdcLog;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.ems.constants.StringConstants.NAME_REGEX;

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
    @Transactional
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        String name = getAvailableName(createEmployeeRequest.getName());
        createEmployeeRequest.setName(name);
        PayrollEmployeeResponse createdPayroll = payrollService.createPayroll(adapter.adaptEmployee(createEmployeeRequest));
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

    private List<EmployeeSalaryResponse> prepareEmployeeSalary(List<Employee> employees) {
        if (employees != null && employees.size() >= 1) {
            List<EmployeeSalaryResponse> employeeSalaryResponse = new LinkedList<>();
            PayrollAllEmployeeResponse payrollDetails = payrollService.fetchEmployees();

            for (Employee employee : employees) {
                for (PayrollEmployee payrollEmployee : payrollDetails.getPayrollEmployee()) {
                    if (employee.getPayrollId().equals(payrollEmployee.getId())) {
                        EmployeeSalaryResponse salaryResponse = new EmployeeSalaryResponse(employee.getId(),
                                employee.getName(), employee.getAge(), Long.parseLong(payrollEmployee.getSalary()));
                        employeeSalaryResponse.add(salaryResponse);
                    }

                }
            }
            return employeeSalaryResponse;
        } else {
            return null;
        }
    }

    private String getAvailableName(String name) {
//        String nameRegex = name + "\\d?$";
        String nameRegex = name + NAME_REGEX;
        List<String> names = employeeJpaService.fetchEmployeeByRegexNmae(nameRegex);
        if (names != null && names.size() >= 1) {
            return name + names.size();
        }
        return name;
    }
}

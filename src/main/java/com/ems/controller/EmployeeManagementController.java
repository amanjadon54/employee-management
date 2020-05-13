package com.ems.controller;

import com.ems.annotation.RequestResponseLog;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.EmployeeSalaryResponse;
import com.ems.service.EmployeeManagerService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

import static com.ems.constants.StringConstants.VALID_SEARCH_NAME_REGEX;

@RestController
@RequestMapping("/v1/employee")
@RequestResponseLog
@Validated
public class EmployeeManagementController {

    @Autowired
    EmployeeManagerService employeeManager;

    @PostMapping("")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = employeeManager.createEmployee(createEmployeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"name"})
    public ResponseEntity<EmployeeSalaryResponse> getEmployeeByName(@RequestParam(name = "name") @Pattern(regexp = VALID_SEARCH_NAME_REGEX) @NotBlank String name)
            throws IOException {
        List<EmployeeSalaryResponse> employees = employeeManager.fetchEmployeeByName(name);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"age"})
    public ResponseEntity<EmployeeSalaryResponse> getEmployeeByAge(@RequestParam(name = "age") @Min(18) @Max(100) int age) {
        List<EmployeeSalaryResponse> employees = employeeManager.fetchEmployeeByAge(age);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping("/all")
    public ResponseEntity<Employee> getAllEmployees() {
        List<Employee> employees = employeeManager.fetchAllEmployees();
        return new ResponseEntity(employees, HttpStatus.OK);
    }


}

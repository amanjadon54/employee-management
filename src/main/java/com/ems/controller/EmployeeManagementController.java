package com.ems.controller;

import com.ems.annotation.RequestResponseLog;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.service.EmployeeManagerService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequestResponseLog
@Validated
public class EmployeeManagementController {

    @Autowired
    EmployeeManagerService employeeManager;

    @PostMapping("")
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = employeeManager.createEmployee(createEmployeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"name"})
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam(name = "name") @NotBlank @Max(50) String name)
            throws IOException {
        List<Employee> employees = employeeManager.fetchEmployeeByName(name);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"age"})
    public ResponseEntity<Employee> getEmployeeByAge(@RequestParam(name = "age") @Min(18) @Max(100) int age) {
        List<Employee> employees = employeeManager.fetchEmployeeByAge(age);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

}

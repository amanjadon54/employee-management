package com.ems.controller;

import com.ems.annotation.RequestResponseLog;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.service.EmployeeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequestResponseLog
public class EmployeeManagementController {

    @Autowired
    EmployeeManagerService employeeManager;

    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = employeeManager.createEmployee(createEmployeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/v1/employee"}, params = {"name"})
    public ResponseEntity<Employee> getemployeeByName(@Valid @RequestParam(name = "name") String name) {
        List<Employee> employees = employeeManager.fetchEmployeeByName(name);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/v1/employee"}, params = {"age"})
    public ResponseEntity<Employee> getEmployeeByAge(@RequestParam(name = "age") int age) {
        List<Employee> employees = employeeManager.fetchEmployeeByAge(age);
        return new ResponseEntity(employees, HttpStatus.OK);
    }

}

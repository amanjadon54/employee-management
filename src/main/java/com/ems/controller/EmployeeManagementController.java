package com.ems.controller;

import com.ems.annotation.RequestResponseLog;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.service.EmployeeManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/employee")
@RequestResponseLog
public class EmployeeManagementController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeManagerService employeeManager;

    @PostMapping("/")
    public ResponseEntity<Integer> createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        logger.info("sample");
        System.out.println("employee call recoeved with validation");
//        employeeManager.createEmployee(createEmployeeRequest);
        employeeManager.fetchEmployeeByAge();
        return null;
    }

}

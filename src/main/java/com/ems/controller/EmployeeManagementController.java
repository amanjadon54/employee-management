package com.ems.controller;

import com.ems.annotation.LoggingAnnotation;
import com.ems.model.requests.CreateEmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeManagementController {

    @LoggingAnnotation
    @PostMapping("/")
    public ResponseEntity<Integer> createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        System.out.println("employee call recoeved with validation");
        return null;
    }

}

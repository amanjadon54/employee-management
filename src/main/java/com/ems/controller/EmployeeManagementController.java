package com.ems.controller;

import com.ems.model.requests.CreateEmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/employee")
public class EmployeeManagementController {

    @PostMapping("/")
    public ResponseEntity<Integer> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        System.out.println("employee call recoeved with validation");
        return null;
    }

}

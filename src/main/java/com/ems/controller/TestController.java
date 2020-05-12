package com.ems.controller;

import com.ems.external.adapter.EmployeePayrollAdapter;
import com.ems.external.service.PayrollService;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    PayrollService payrollService;

    @GetMapping("/test/{id}")
    public ResponseEntity<PayrollEmployeeResponse> test(@PathVariable String id) {
        PayrollEmployeeResponse absc = payrollService.getPayrollById(id);
        return new ResponseEntity(absc, HttpStatus.OK);
    }

    @GetMapping("/test/employees")
    public ResponseEntity<PayrollAllEmployeeResponse> test() {
        PayrollAllEmployeeResponse absc = payrollService.fetchEmployees();
        return new ResponseEntity(absc, HttpStatus.OK);
    }

    @PostMapping("/test/create")
    public ResponseEntity<PayrollEmployeeResponse> test(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        EmployeePayrollAdapter adapter = new EmployeePayrollAdapter();
        PayrollEmployeeResponse absc = payrollService.createPayroll(adapter.adaptEmployee(createEmployeeRequest));
        return new ResponseEntity(absc, HttpStatus.OK);
    }

}

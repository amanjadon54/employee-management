package com.ems.controller;

import com.ems.annotation.RequestResponseLog;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployeeResponse;
import com.ems.service.EmployeeManagerService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequestResponseLog
public class EmployeeManagementController {

    @Autowired
    EmployeeManagerService employeeManager;

    @PostMapping("")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = employeeManager.createEmployee(createEmployeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"name"})
    public ResponseEntity<Employee> getemployeeByName(@Valid @RequestParam(name = "name") String name)
            throws IOException {
        List<Employee> employees = employeeManager.fetchEmployeeByName(name);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://dummy.restapiexample.com/api/v1/employee/1")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();


        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"age"})
    public ResponseEntity<Employee> getEmployeeByAge(@RequestParam(name = "age") int age) {
//        List<Employee> employees = employeeManager.fetchEmployeeByAge(age);
        PayrollEmployeeResponse response = employeeManager.fetchEmployeeByAge(age);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}

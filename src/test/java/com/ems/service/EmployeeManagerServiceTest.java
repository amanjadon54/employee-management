package com.ems.service;

import com.ems.external.service.PayrollService;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;
import com.ems.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerServiceTest {

    @InjectMocks
    EmployeeManagerService employeeManagerService;

    @Mock
    EmployeeJpaService employeeJpaService;

    @Mock
    PayrollService payrollService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void createEmployeeTest() {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest("Aman", 27, 20000);
        String payrollId = "2";
        Employee expectedEmployee = new Employee(1, employeeRequest.getName(), employeeRequest.getAge(), payrollId, null, null);
        PayrollEmployeeResponse expectedPayroll = new PayrollEmployeeResponse("success",
                new PayrollEmployee(payrollId, employeeRequest.getName(),
                        String.valueOf(employeeRequest.getSalary()),
                        String.valueOf(employeeRequest.getAge())));

        when(payrollService.createPayroll(any(PayrollEmployee.class))).thenReturn(expectedPayroll);
        when(employeeJpaService.createEmployee(employeeRequest, payrollId)).thenReturn(expectedEmployee);
        Employee employee = employeeManagerService.createEmployee(employeeRequest);

        assertEquals("Unequal age", employeeRequest.getAge(), expectedEmployee.getAge());
        assertEquals("Unequal name", employeeRequest.getName().toLowerCase(), expectedEmployee.getName().toLowerCase());
        assertEquals("Unequal Salary", payrollId, expectedEmployee.getPayrollId());
    }

//    @Test
//    public void testFectchEmployeeByName() {
//        Employee employee = new Employee(1, "test data", 27, "2", null, null);
//        LinkedList<Employee> employeeList = new LinkedList<>();
//        employeeList.add(employee);
//        when(employeeJpaService.fetchEmployeeByName(employee.getName())).thenReturn(employeeList);
//        assertEquals("Find by Name does not match", employeeRepository.findByNameContaining(employee.getName()), employeeList);
//
//    }
//
//    @Test
//    public void testFectchEmployeeByRegexName() {
//        Employee employee = new Employee(1, "test data", 27, "2", null, null);
//        LinkedList<String> employeeNames = new LinkedList<>();
//        employeeNames.add(employee.getName());
//        when(employeeRepository.findByName(employee.getName())).thenReturn(employeeNames);
//
//        assertEquals("Find by Name Regex does not match", employeeRepository.findByName(employee.getName()), employeeNames);
//
//    }

//    @Test
//    public void testFectchEmployeeByAge() {
//        Employee employee = new Employee(1, "test data", 27, "2", null, null);
//        LinkedList<Employee> employeeList = new LinkedList<>();
//        employeeList.add(employee);
//        when(employeeRepository.findByAge(employee.getAge())).thenReturn(employeeList);
//        assertEquals("Find by name does not match", employeeRepository.findByAge(employee.getAge()), employeeList);
//
//    }
}

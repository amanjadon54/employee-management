package com.ems.service;

import com.ems.constants.PayrollStatus;
import com.ems.external.service.PayrollService;
import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.model.response.EmployeeSalaryResponse;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;
import com.ems.test.util.EmployeeRequestProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.ems.StringConstants.TEST_VALIDATION_FAILS;
import static com.ems.test.util.EmployeeAttributesConstants.*;
import static com.ems.test.util.EmployeeDataProvider.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerServiceTest {

    @InjectMocks
    EmployeeManagerService employeeManagerService;

    @Mock
    EmployeeJpaService employeeJpaService;

    @Mock
    PayrollService payrollService;


    @Test
    public void createEmployeeTest() {
        CreateEmployeeRequest employeeRequest = EmployeeRequestProvider.createValidEmployee();
        String payrollId = "2";
        Employee expectedEmployee = new Employee(1, employeeRequest.getName(), employeeRequest.getAge(), payrollId, null, null);
        PayrollEmployeeResponse expectedPayroll = new PayrollEmployeeResponse(PayrollStatus.SUCCESS,
                new PayrollEmployee(payrollId, employeeRequest.getName(),
                        String.valueOf(employeeRequest.getSalary()),
                        String.valueOf(employeeRequest.getAge())));

        when(payrollService.createPayroll(any(PayrollEmployee.class))).thenReturn(expectedPayroll);
        when(employeeJpaService.createEmployee(employeeRequest, payrollId)).thenReturn(expectedEmployee);
        Employee employee = employeeManagerService.createEmployee(employeeRequest);

        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, AGE), employeeRequest.getAge(), expectedEmployee.getAge());
        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, NAME), employeeRequest.getName().toLowerCase(), expectedEmployee.getName().toLowerCase());
        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, SALARY), payrollId, expectedEmployee.getPayrollId());
    }

    @Test
    public void testFectchEmployeeByName() {
        String name = "aman";
        List<Employee> filteredEmployee = fetchEmployeeByName(name);
        PayrollAllEmployeeResponse payrollRecords = payrollEmployees;
        when(employeeJpaService.fetchEmployeeByName(name)).thenReturn(filteredEmployee);
        when(payrollService.fetchEmployees()).thenReturn(payrollRecords);
        List<EmployeeSalaryResponse> employeesSalary = employeeManagerService.fetchEmployeeByName(name);
        assertEquals(String.format(TEST_VALIDATION_FAILS, EMPLOYEE_BY_NAME, SALARY), employeesSalary.size(), filteredEmployee.size());
        assertTrue((String.format(TEST_VALIDATION_FAILS, EMPLOYEE_BY_NAME, NAME)), verifyEmployeeSalaryRecords(filteredEmployee, payrollRecords.getPayrollEmployee(), employeesSalary));
    }

    @Test
    public void testFectchEmployeeByAge() {
        int age = 26;
        List<Employee> filteredEmployee = fetchEmployeesByAge(age);
        PayrollAllEmployeeResponse payrollRecords = payrollEmployees;
        when(employeeJpaService.fetchEmployeeByAge(age)).thenReturn(filteredEmployee);
        when(payrollService.fetchEmployees()).thenReturn(payrollRecords);
        List<EmployeeSalaryResponse> employeesSalary = employeeManagerService.fetchEmployeeByAge(age);
        assertEquals(employeesSalary.size(), filteredEmployee.size());
        assertTrue(verifyEmployeeSalaryRecords(filteredEmployee, payrollRecords.getPayrollEmployee(), employeesSalary));

    }

}


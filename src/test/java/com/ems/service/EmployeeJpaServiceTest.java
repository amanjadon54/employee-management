package com.ems.service;

import com.ems.model.Employee;
import com.ems.model.requests.CreateEmployeeRequest;
import com.ems.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;

import static com.ems.StringConstants.*;
import static com.ems.test.util.EmployeeAttributesConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeJpaServiceTest {

    @InjectMocks
    EmployeeJpaService employeeJpaService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void createEmployeeTest() {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest("Aman", 27, 20000);
        when(employeeRepository.save(any(Employee.class))).thenReturn(null);
        Employee employee = employeeJpaService.createEmployee(employeeRequest, "1");

        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, NAME), employeeRequest.getAge(), employee.getAge());
        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, AGE), employeeRequest.getName(), employee.getName());
        assertEquals(String.format(TEST_VALIDATION_FAILS, CREATE_EMPLOYEE, SALARY), "1", employee.getPayrollId());
    }

    @Test
    public void testFectchEmployeeByName() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findByNameContaining(employee.getName())).thenReturn(employeeList);
        assertEquals(String.format(TEST_FAIL, EMPLOYEE_BY_NAME), employeeJpaService.fetchEmployeeByName(employee.getName()), employeeList);

    }

    @Test
    public void testFectchEmployeeByNameNotThere() {
        when(employeeRepository.findByNameContaining(any(String.class))).thenReturn(null);
        assertEquals(String.format(TEST_VALIDATION_FAILS, EMPLOYEE_BY_NAME, NAME_NOT_PRESENT), employeeJpaService.fetchEmployeeByName(""), null);
    }

    @Test
    public void testFectchEmployeeByRegexName() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<String> employeeNames = new LinkedList<>();
        employeeNames.add(employee.getName());
        when(employeeRepository.findByName(employee.getName())).thenReturn(employeeNames);
        assertEquals(String.format(TEST_FAIL, EMPLOYEE_BY_REGEX_NAME), employeeJpaService.fetchEmployeeByRegexNmae(employee.getName()), employeeNames);

    }

    @Test
    public void testFectchEmployeeByRegexNameNotThere() {
        when(employeeRepository.findByName("")).thenReturn(null);
        assertEquals(String.format(TEST_VALIDATION_FAILS, EMPLOYEE_BY_REGEX_NAME, NAME_NOT_PRESENT), employeeJpaService.fetchEmployeeByRegexNmae(""), null);

    }

    @Test
    public void testFectchEmployeeByAge() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findByAge(employee.getAge())).thenReturn(employeeList);
        assertEquals(String.format(TEST_FAIL, EMPLOYEE_BY_AGE), employeeJpaService.fetchEmployeeByAge(employee.getAge()), employeeList);

    }

    @Test
    public void testFectchEmployeeByAgeNotThere() {
        when(employeeRepository.findByAge(any(Integer.class))).thenReturn(null);
        assertEquals(String.format(TEST_VALIDATION_FAILS, EMPLOYEE_BY_AGE, AGE_NOT_PRESENT), employeeJpaService.fetchEmployeeByAge(97), null);

    }


}

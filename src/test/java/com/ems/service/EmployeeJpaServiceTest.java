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

        assertEquals("Unequal age", employeeRequest.getAge(), employee.getAge());
        assertEquals("Unequal name", employeeRequest.getName(), employee.getName());
        assertEquals("Unequal Salary", "1", employee.getPayrollId());
    }

    @Test
    public void testFectchEmployeeByName() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findByNameContaining(employee.getName())).thenReturn(employeeList);
        assertEquals("Find by Name does not match", employeeRepository.findByNameContaining(employee.getName()), employeeList);

    }

    @Test
    public void testFectchEmployeeByRegexName() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<String> employeeNames = new LinkedList<>();
        employeeNames.add(employee.getName());
        when(employeeRepository.findByName(employee.getName())).thenReturn(employeeNames);
        assertEquals("Find by Name Regex does not match", employeeRepository.findByName(employee.getName()), employeeNames);

    }

    @Test
    public void testFectchEmployeeByAge() {
        Employee employee = new Employee(1, "test data", 27, "2", null, null);
        LinkedList<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findByAge(employee.getAge())).thenReturn(employeeList);
        assertEquals("Find by name does not match", employeeRepository.findByAge(employee.getAge()), employeeList);

    }


}

package com.ems.api;

import com.ems.model.Employee;

import java.util.List;

public interface EmployeeManager {

    Employee createEmployee();

    List<Employee> fetchEmployeeByName();

    List<Employee> fetchEmployeeByAge();

}

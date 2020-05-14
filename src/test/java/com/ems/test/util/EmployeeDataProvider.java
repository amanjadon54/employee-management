package com.ems.test.util;

import com.ems.constants.PayrollStatus;
import com.ems.model.Employee;
import com.ems.model.response.EmployeeSalaryResponse;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployee;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDataProvider {

    public static List<Employee> employees = null;
    public static PayrollAllEmployeeResponse payrollEmployees = null;

    static {
        payrollEmployees = prepareAllEmployeesResponseData();
        employees = fetchEmployeesData();
    }

    public static PayrollAllEmployeeResponse prepareAllEmployeesResponseData() {
        PayrollEmployee payrollEmployee = new PayrollEmployee("10", "aman", "20000", "27");
        PayrollEmployee payrollEmployee1 = new PayrollEmployee("11", "rahul", "3000", "27");
        PayrollEmployee payrollEmployee2 = new PayrollEmployee("12", "krishna", "30000", "26");
        PayrollEmployee payrollEmployee3 = new PayrollEmployee("13", "anand", "11000", "26");
        PayrollEmployee payrollEmployee4 = new PayrollEmployee("14", "shirish", "23000", "26");
        PayrollEmployee payrollEmployee5 = new PayrollEmployee("15", "varun", "15000", "24");
        PayrollEmployee payrollEmployee6 = new PayrollEmployee("16", "yash", "40000", "24");
        PayrollEmployee payrollEmployee7 = new PayrollEmployee("17", "aman2", "21000", "26");
        PayrollEmployee payrollEmployee8 = new PayrollEmployee("18", "aman3", "31000", "24");
        PayrollEmployee payrollEmployee9 = new PayrollEmployee("19", "aman4", "41000", "24");

        List<PayrollEmployee> payrollEmployeeList = new LinkedList<>();
        payrollEmployeeList.add(payrollEmployee);
        payrollEmployeeList.add(payrollEmployee1);
        payrollEmployeeList.add(payrollEmployee2);
        payrollEmployeeList.add(payrollEmployee3);
        payrollEmployeeList.add(payrollEmployee4);
        payrollEmployeeList.add(payrollEmployee5);
        payrollEmployeeList.add(payrollEmployee6);
        payrollEmployeeList.add(payrollEmployee7);
        payrollEmployeeList.add(payrollEmployee8);
        payrollEmployeeList.add(payrollEmployee9);

        PayrollAllEmployeeResponse response = new PayrollAllEmployeeResponse(PayrollStatus.SUCCESS, payrollEmployeeList);
        return response;
    }

    public static List<Employee> fetchEmployeesData() {
        Employee employee = new Employee(1, "aman", 27, "10", null, null);
        Employee employee1 = new Employee(2, "rahul", 27, "11", null, null);
        Employee employee2 = new Employee(3, "krishna", 26, "12", null, null);
        Employee employee3 = new Employee(4, "anand", 26, "13", null, null);
        Employee employee4 = new Employee(5, "shirish", 26, "14", null, null);
        Employee employee5 = new Employee(6, "varun", 24, "15", null, null);
        Employee employee6 = new Employee(7, "yash", 24, "16", null, null);
        Employee employee7 = new Employee(8, "aman2", 26, "17", null, null);
        Employee employee8 = new Employee(9, "aman3", 24, "18", null, null);
        Employee employee9 = new Employee(10, "aman4", 24, "19", null, null);
        Employee employee10 = new Employee(10, "notfound", 31, "30", null, null);
        Employee employee11 = new Employee(10, "notfound1", 31, "30", null, null);
        List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        employeeList.add(employee4);
        employeeList.add(employee5);
        employeeList.add(employee6);
        employeeList.add(employee7);
        employeeList.add(employee8);
        employeeList.add(employee9);
        employeeList.add(employee10);
        employeeList.add(employee11);
        return employeeList;
    }

    public static List<Employee> fetchEmployeeByName(String name) {
        return employees.stream()
                .filter(employee -> employee.getName().toLowerCase()
                        .contains(name))
                .collect(Collectors.toList());
    }

    public static List<Employee> fetchEmployeesByAge(int age) {
        return employees.stream()
                .filter(employee -> employee.getAge() == age)
                .collect(Collectors.toList());
    }

    public static boolean verifyEmployeeSalaryRecords(List<Employee> employeeList, List<PayrollEmployee> payrollEmployees, List<EmployeeSalaryResponse> salaryResponses) {
        HashMap<Integer, String> employees = new HashMap<>();
        HashMap<String, String> salaries = new HashMap<>();
        employeeList.forEach(emp -> employees.putIfAbsent(emp.getId(), emp.getPayrollId()));
        payrollEmployees.forEach(emp -> salaries.putIfAbsent(emp.getId(), emp.getSalary()));

        for (EmployeeSalaryResponse employeeSalary : salaryResponses) {
            if (employeeSalary.getSalary() != Long.valueOf(salaries.get(employees.get(employeeSalary.getId())))) {
                return false;
            }
        }
        return true;
    }


}

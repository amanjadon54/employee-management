package com.ems.controller;

import com.ems.model.response.EmployeeSalaryResponse;
import com.ems.service.EmployeeManagerService;
import com.ems.test.util.EmployeeRequestProvider;
import com.ems.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static com.ems.StringConstants.APPLICATION_JSON;
import static com.ems.StringConstants.EMPLOYEE_BASE_API;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagementControllerTest {

    @InjectMocks
    EmployeeManagementController employeeManagementController;

    private MockMvc mockMvc;

    @Mock
    EmployeeManagerService employeeManagerService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeManagementController).build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        mockMvc.perform(post(EMPLOYEE_BASE_API)
                .contentType(APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(EmployeeRequestProvider.createValidEmployee())))
                .andExpect(status().isCreated());

    }

    @Test
    public void testCreateEmployeeWithInvalidAge() throws Exception {
        mockMvc.perform(post(EMPLOYEE_BASE_API)
                .contentType(APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(EmployeeRequestProvider.createInValidAgeEmployee())))
                .andExpect(status().isBadRequest());
        verifyZeroInteractions(employeeManagerService);
    }

    @Test
    public void testCreateEmployeeWithInvalidSalary() throws Exception {
        mockMvc.perform(post(EMPLOYEE_BASE_API)
                .contentType(APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(EmployeeRequestProvider.createInValidSalaryEmployee())))
                .andExpect(status().isBadRequest());
        verifyZeroInteractions(employeeManagerService);
    }

    @Test
    public void testCreateEmployeeWithInvalidName() throws Exception {
        mockMvc.perform(post(EMPLOYEE_BASE_API)
                .contentType(APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(EmployeeRequestProvider.createInValidNameEmployee())))
                .andExpect(status().isBadRequest());
        verifyZeroInteractions(employeeManagerService);
    }


    @Test
    public void testGetEmployeeByName() throws Exception {
        EmployeeSalaryResponse employeeSalaryResponse = new EmployeeSalaryResponse(1, "aman", 27, 20000);
        EmployeeSalaryResponse employeeSalaryResponse2 = new EmployeeSalaryResponse(2, "aman1", 28, 30000);
        List<EmployeeSalaryResponse> employeeSalaryResponseList = new LinkedList<>();
        employeeSalaryResponseList.add(employeeSalaryResponse);
        employeeSalaryResponseList.add(employeeSalaryResponse2);

        String name = "aman";
        when(employeeManagerService.fetchEmployeeByName(name)).thenReturn(employeeSalaryResponseList);

        mockMvc.perform(get(EMPLOYEE_BASE_API).param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("aman")))
                .andExpect(jsonPath("$[0].age", is(27)))
                .andExpect(jsonPath("$[0].salary", is(20000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("aman1")))
                .andExpect(jsonPath("$[1].age", is(28)))
                .andExpect(jsonPath("$[1].salary", is(30000)));

        verify(employeeManagerService, times(1)).fetchEmployeeByName(name);
        verifyNoMoreInteractions(employeeManagerService);
    }

//    @Test
//    public void testGetEmployeeByNameNotFound() throws Exception {
//        String name = "not found";
//        when(employeeManagerService.fetchEmployeeByName(name)).thenThrow(new EmployeeNotFoundException("", ""));
//
//        mockMvc.perform(get("/v1/employee").param("name", name))
//                .andExpect(status().isInternalServerError());
//
//        verify(employeeManagerService, times(1)).fetchEmployeeByName(name);
//        verifyNoMoreInteractions(employeeManagerService);
//    }


    @Test
    public void testGetEmployeeByAge() throws Exception {
        EmployeeSalaryResponse employeeSalaryResponse = new EmployeeSalaryResponse(1, "aman", 27, 20000);
        EmployeeSalaryResponse employeeSalaryResponse2 = new EmployeeSalaryResponse(2, "aman1", 27, 30000);
        List<EmployeeSalaryResponse> employeeSalaryResponseList = new LinkedList<>();
        employeeSalaryResponseList.add(employeeSalaryResponse);
        employeeSalaryResponseList.add(employeeSalaryResponse2);
        int age = 27;
        when(employeeManagerService.fetchEmployeeByAge(age)).thenReturn(employeeSalaryResponseList);

        mockMvc.perform(get(EMPLOYEE_BASE_API).param("age", String.valueOf(age)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("aman")))
                .andExpect(jsonPath("$[0].age", is(27)))
                .andExpect(jsonPath("$[0].salary", is(20000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("aman1")))
                .andExpect(jsonPath("$[1].age", is(27)))
                .andExpect(jsonPath("$[1].salary", is(30000)));

        verify(employeeManagerService, times(1)).fetchEmployeeByAge(age);
        verifyNoMoreInteractions(employeeManagerService);
    }
}


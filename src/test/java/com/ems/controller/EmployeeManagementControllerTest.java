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
import java.util.List;

import static com.ems.StringConstants.*;
import static com.ems.test.util.EmployeeRequestProvider.prepareEmployeeSalaryList;
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
        List<EmployeeSalaryResponse> employeeSalaryResponseList = prepareEmployeeSalaryList();
        String name = employeeSalaryResponseList.get(0).getName();
        when(employeeManagerService.fetchEmployeeByName(name)).thenReturn(employeeSalaryResponseList);
        mockMvc.perform(get(EMPLOYEE_BASE_API).param(NAME, name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employeeSalaryResponseList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(employeeSalaryResponseList.get(0).getName())))
                .andExpect(jsonPath("$[0].age", is(employeeSalaryResponseList.get(0).getAge())))
                .andExpect(jsonPath("$[0].salary", is((int) (employeeSalaryResponseList.get(0).getSalary()))))
                .andExpect(jsonPath("$[1].id", is(employeeSalaryResponseList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(employeeSalaryResponseList.get(1).getName())))
                .andExpect(jsonPath("$[1].age", is(employeeSalaryResponseList.get(1).getAge())))
                .andExpect(jsonPath("$[1].salary", is((int)(employeeSalaryResponseList.get(1).getSalary()))));

        verify(employeeManagerService, times(1)).fetchEmployeeByName(name);
        verifyNoMoreInteractions(employeeManagerService);
    }


    @Test
    public void testGetEmployeeByAge() throws Exception {

        List<EmployeeSalaryResponse> employeeSalaryResponseList = prepareEmployeeSalaryList();
        int age = employeeSalaryResponseList.get(0).getAge();
        when(employeeManagerService.fetchEmployeeByAge(age)).thenReturn(employeeSalaryResponseList);

        mockMvc.perform(get(EMPLOYEE_BASE_API).param(AGE, String.valueOf(age)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employeeSalaryResponseList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(employeeSalaryResponseList.get(0).getName())))
                .andExpect(jsonPath("$[0].age", is(employeeSalaryResponseList.get(0).getAge())))
                .andExpect(jsonPath("$[0].salary", is((int)(employeeSalaryResponseList.get(0).getSalary()))))
                .andExpect(jsonPath("$[1].id", is(employeeSalaryResponseList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(employeeSalaryResponseList.get(1).getName())))
                .andExpect(jsonPath("$[1].age", is(employeeSalaryResponseList.get(1).getAge())))
                .andExpect(jsonPath("$[1].salary", is((int)(employeeSalaryResponseList.get(1).getSalary()))));

        verify(employeeManagerService, times(1)).fetchEmployeeByAge(age);
        verifyNoMoreInteractions(employeeManagerService);
    }
}


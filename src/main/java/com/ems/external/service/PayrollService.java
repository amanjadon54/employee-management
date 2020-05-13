package com.ems.external.service;

import com.ems.annotation.MdcLog;
import com.ems.external.RestApiManager;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static com.ems.constants.HttpConstants.*;
import static com.ems.constants.PayrollApiMapper.*;

@Service
public class PayrollService extends RestApiManager {

    @Value("${external.payroll.base.url}")
    private String payrollBaseUrl;

    @Value("${external.payroll.create.read.timeout}")
    private int createEmployeeReadTimeout;

    @Value("${external.payroll.get.read.timeout}")
    private int getPayrollReadTimeout;

    @MdcLog
    public PayrollEmployeeResponse getPayrollById(String payrollId) {
        return super.get(payrollBaseUrl, String.format(EMPLOYEE_BY_PARAM, payrollId), null, getRequestHeaders(true), PayrollEmployeeResponse.class, getPayrollReadTimeout);
    }

    @MdcLog
    public PayrollEmployeeResponse createPayroll(PayrollEmployee payrollEmployee) {
        return super.post(payrollBaseUrl, CREATE_EMPLOYEE, null, payrollEmployee, getRequestHeaders(false), PayrollEmployeeResponse.class, createEmployeeReadTimeout);
    }

    @MdcLog
    public PayrollAllEmployeeResponse fetchEmployees() {
        return super.get(payrollBaseUrl, ALL_EMPLOYEES, null, getRequestHeaders(false), PayrollAllEmployeeResponse.class, getPayrollReadTimeout);
    }


    private HttpHeaders getRequestHeaders(boolean cookieNeeded) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(APPLICATION, JSON));
        requestHeaders.add(HttpHeaders.COOKIE, COOKIE);
        return requestHeaders;
    }
}

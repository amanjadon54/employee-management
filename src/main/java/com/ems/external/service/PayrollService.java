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

    @MdcLog
    public PayrollEmployeeResponse getPayrollById(String payrollId) {
        return super.get(payrollBaseUrl, String.format(EMPLOYEE_BY_PARAM, payrollId), null, getRequestHeaders(true), PayrollEmployeeResponse.class, 10000);
    }

    @MdcLog
    public PayrollEmployeeResponse getPayrollById(int payrollId) {
        return super.get(payrollBaseUrl, String.format(EMPLOYEE_BY_PARAM, payrollId), null, getRequestHeaders(true), PayrollEmployeeResponse.class, 10000);
    }

    @MdcLog
    public PayrollEmployeeResponse createPayroll(PayrollEmployee payrollEmployee) {
        return super.post(payrollBaseUrl, CREATE_EMPLOYEE, null, payrollEmployee, getRequestHeaders(false), PayrollEmployeeResponse.class, 10000);
    }

    @MdcLog
    public PayrollAllEmployeeResponse fetchEmployees() {
        return super.get(payrollBaseUrl, ALL_EMPLOYEES, null, getRequestHeaders(false), PayrollAllEmployeeResponse.class, 10000);
    }


    private HttpHeaders getRequestHeaders(boolean cookieNeeded) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(APPLICATION, JSON));
        requestHeaders.add(HttpHeaders.COOKIE, COOKIE);
        return requestHeaders;
    }
}

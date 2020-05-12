package com.ems.external.service;

import com.ems.annotation.MdcLog;
import com.ems.constants.HttpConstants;
import com.ems.external.RestApiManager;
import com.ems.model.response.PayrollAllEmployeeResponse;
import com.ems.model.response.PayrollEmployee;
import com.ems.model.response.PayrollEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class PayrollService extends RestApiManager {

    @Value("${external.payroll.base.url}")
    private String payrollBaseUrl;

    private final static String EMPLOYEE_BY_PARAM = "/api/v1/employee/%s";
    private final static String CREATE_EMPLOYEE = "/api/v1/create";
    private final static String ALL_EMPLOYEES = "/api/v1/employees";
    public static final String APPLICATION = "application";
    public static final String JSON = "json";

    Logger log = LoggerFactory.getLogger(this.getClass());

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
        if (cookieNeeded)
            requestHeaders.add(HttpConstants.IS_COOKIE, "");
        return requestHeaders;
    }
}

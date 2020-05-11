package com.ems.external.service;

import com.ems.annotation.MdcLog;
import com.ems.external.RestApiManager;
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
    public static final String APPLICATION = "application";
    public static final String JSON = "json";

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @MdcLog
    public PayrollEmployeeResponse fetchEmployeePayroll(int payrollId) {
        return super.get(payrollBaseUrl, String.format(EMPLOYEE_BY_PARAM, payrollId), null, getRequestHeaders(), PayrollEmployeeResponse.class, 10000);
    }

    @MdcLog
    public PayrollEmployeeResponse createPayroll(PayrollEmployee payrollEmployee) {
        payrollEmployee.setId("1");
        return super.post(payrollBaseUrl, CREATE_EMPLOYEE, null, payrollEmployee, getRequestHeaders(), PayrollEmployeeResponse.class, 10000);
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(APPLICATION, JSON));
        return requestHeaders;
    }
}

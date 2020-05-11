package com.ems.external.service;

import com.ems.external.RestApiManager;
import com.ems.model.response.PayrollEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class PayrollService extends RestApiManager {

    @Value("${external.payroll.base.url}")
    private String payrollBaseUrl;

    private final static String EMPLOYEE_BY_PARAM = "employee/%s";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public PayrollEmployeeResponse fetchEmployeePayroll(int payrollId) {
        logger.info("fetchEmployeePayrollCalled with some aprams");
        super.get(payrollBaseUrl,String.format(EMPLOYEE_BY_PARAM,payrollId),null,getRequestHeaders(),PayrollEmployeeResponse.class,10000);
        return null;
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }
}

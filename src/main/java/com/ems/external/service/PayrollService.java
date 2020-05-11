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

import java.util.Arrays;

@Service
public class PayrollService extends RestApiManager {

    @Value("${external.payroll.base.url}")
    private String payrollBaseUrl;

    private final static String EMPLOYEE_BY_PARAM = "/api/v1/employee/%s";
    private final static String CREATE_EMPLOYEE = "/api/v1/create";

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public PayrollEmployeeResponse fetchEmployeePayroll(int payrollId) {
        logger.info("fetchEmployeePayrollCalled with some aprams");
//        return super.get(payrollBaseUrl, String.format(EMPLOYEE_BY_PARAM, payrollId), null, getRequestHeaders(), PayrollEmployeeResponse.class, 10000);
        return super.get(payrollBaseUrl, EMPLOYEE_BY_PARAM, null, getRequestHeaders(), PayrollEmployeeResponse.class, 10000);
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        requestHeaders.setContentType(new MediaType(APPLICATION, JSON));
        requestHeaders.setCacheControl("no-cache");
        return requestHeaders;
    }

    public static final String APPLICATION = "application";
    public static final String JSON = "json";
}

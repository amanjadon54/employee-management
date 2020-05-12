package com.ems.interceptor;

import com.ems.exception.ApiError;
import com.ems.exception.EmployeeManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class StatefulRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String cookie;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        try {
            return executeWithCookie(request, body, execution);
        } catch (IOException e) {
            log.error("Error in Rest Interceptor" + e);
            throw new EmployeeManagementException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getLocalizedMessage()));
        }
    }

    private ClientHttpResponse executeWithCookie(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = null;
        if (cookie == null) {
            response = execution.execute(request, body);
            cookie = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
        } else {
            request.getHeaders().add(HttpHeaders.COOKIE, cookie);
            response = execution.execute(request, body);
        }
        return response;
    }

    private String concatenateAllCookies(List<String> cookies) {
        String cookie = null;
        cookie = cookies.stream().reduce((x, value) -> x + value).get();
        return cookie;
    }

}

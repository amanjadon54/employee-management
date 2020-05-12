package com.ems.interceptor;

import com.ems.constants.HttpConstants;
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

    private List<String> cookies;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        try {
            if (request.getHeaders().containsKey(HttpConstants.IS_COOKIE)) {
                return executeWithCookie(request, body, execution);
            }
            return execution.execute(request, body);
        } catch (IOException e) {
            log.error("Error in Rest Interceptor" + e);
            throw new EmployeeManagementException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getLocalizedMessage()));
        }
    }

    private ClientHttpResponse executeWithCookie(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = null;
        if (cookies == null) {
            ClientHttpResponse cookieResponse = execution.execute(request, body);
            cookies = cookieResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
        }
        request.getHeaders().add(HttpHeaders.COOKIE, concatenateAllCookies(cookies));
        response = execution.execute(request, body);
        cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        return response;
    }

    private String concatenateAllCookies(List<String> cookies) {
        String cookie = null;
        cookies.stream().map(x -> cookie + x);
        return cookie;
    }

}

package com.ems.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class StatefulRestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private List<String> cookies;
    private String finalCookies=null;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (cookies != null) {
            String cookie= cookies.get(0);
            for(int i =1;i<cookies.size();i++){
                cookie+=";"+cookies.get(i);
            }

            request.getHeaders().add(HttpHeaders.COOKIE,cookie );
        }
        ClientHttpResponse response = execution.execute(request, body);

        if (cookies == null) {
            cookies=response.getHeaders().get(HttpHeaders.SET_COOKIE);
        }
        return response;
    }
}

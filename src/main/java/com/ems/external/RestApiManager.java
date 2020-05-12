package com.ems.external;

import com.ems.exception.ApiError;
import com.ems.exception.EmployeeManagementException;
import com.ems.util.TransformUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestApiManager {

    @Autowired
    private RestTemplate restTemplate;

    private static final Gson gson = new Gson();

    private static final Logger log = LoggerFactory.getLogger(RestApiManager.class);

    public <T> T get(String baseUrl, String url, String query, HttpHeaders requestHeaders,
                     Class<T> responseClassType, int readTimeout) {
        ResponseEntity<T> responseEntity = null;
        try {
            String fullUrl = getFullUrl(baseUrl, url, query);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestHeaders);
            log.info("The URL called : {} and readTimeout sent : {} with logId : {}", fullUrl, readTimeout);
            responseEntity = restTemplate.exchange(fullUrl, HttpMethod.GET, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
        } catch (Exception e) {
            log.error("Error in RestApiManager:get : {} ; Exception : {}", responseEntity, e);
            if (e instanceof HttpClientErrorException) {
                ApiError apiError = new ApiError(((HttpClientErrorException) e).getStatusCode(), e.getMessage(), ((HttpClientErrorException) e).getResponseBodyAsString());
                throw new EmployeeManagementException(apiError);
            } else if (responseEntity != null) {
                ApiError apiError = new ApiError(responseEntity.getStatusCode(), e.getMessage(), responseEntity.toString());
                throw new EmployeeManagementException(apiError);
            } else {
                throw new EmployeeManagementException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Get service failing ", null));
            }
        }
        return null;
    }

    public <T> T post(String baseUrl, String url, String query, Object body,
                      HttpHeaders requestHeaders, Class<T> responseClassType, int readTimeout) {
        ResponseEntity<T> responseEntity = null;
        try {
            String fullUrl = getFullUrl(baseUrl, url, query);
            String bodyJson = null;
            if (body != null) {
                bodyJson = TransformUtil.toJson(body);
            }
            HttpEntity<Object> requestEntity = new HttpEntity<>(bodyJson, requestHeaders);
            log.info("The URL called : {} and readTimeout sent : {}", fullUrl, readTimeout);

            responseEntity =
                    restTemplate.exchange(fullUrl, HttpMethod.POST, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
        } catch (Exception e) {
            log.error("Error in RestApiManager:get : {} ; Exception : {}", responseEntity, e);
            if (e instanceof HttpClientErrorException) {
                ApiError apiError = new ApiError(((HttpClientErrorException) e).getStatusCode(), e.getMessage(), ((HttpClientErrorException) e).getResponseBodyAsString());
                throw new EmployeeManagementException(apiError);
            } else if (responseEntity != null) {
                ApiError apiError = new ApiError(responseEntity.getStatusCode(), e.getMessage(), responseEntity.toString());
                throw new EmployeeManagementException(apiError);
            } else {
                throw new EmployeeManagementException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Post service failing ", null));
            }
        }
        return null;
    }

    private String getFullUrl(String baseUrl, String url, String query) {
        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(baseUrl);
        if (url != null) {
            fullUrl.append(url);
        }
        if (query != null && query.startsWith("?")) {
            query = query.substring(1);
        }
        query = StringUtils.trimToNull(query);
        if (query != null) {
            fullUrl.append("?");
            fullUrl.append(query);
        }
        return fullUrl.toString();
    }

    private static String toJson(Object obj) {
        try {
            if (obj != null) {
                return gson.toJson(obj);
            }
        } catch (JsonParseException e) {
            log.error("Error in toJson(), obj: " + obj + " ; Exception: " + e.getMessage());
        }
        return null;
    }


}

package com.ems.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestApiManager {

    @Autowired
    RestTemplate restTemplate;


}

package com.ems.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayrollEmployee {

    private String id;

    @JsonAlias("employee_name")
    private String empName;

    @JsonAlias("employee_salary")
    private String salary;

    @JsonAlias("employee_age")
    private String age;

}


/**
 * {
 * * "status": "success",
 * * "data": {
 * * "id": "1",
 * * "employee_name": "Tiger Nixon",
 * * "employee_salary": "320800",
 * * "employee_age": "61",
 * * "profile_image": ""
 * * }
 * * }
 */

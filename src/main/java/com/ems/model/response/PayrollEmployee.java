package com.ems.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayrollEmployee {

    private int id;

    @JsonProperty("employee_name")
    private String empName;

    @JsonProperty("employee_salary")
    private long salary;

    @JsonProperty("employee_age")
    private int age;

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

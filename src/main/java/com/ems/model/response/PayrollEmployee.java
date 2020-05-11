package com.ems.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayrollEmployee {

    private String id;

    @JsonProperty("name")
    @JsonAlias("employee_name")
    private String name;

    @JsonProperty("salary")
    @JsonAlias("employee_salary")
    private String salary;

    @JsonProperty("age")
    @JsonAlias("employee_age")
    private String age;

    public PayrollEmployee(String name, String salary, String age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

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

package com.ems.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayrollEmployee {

    private int id;
    private String empName;
    private long salary;
    private int age;
    private String profileImage;
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

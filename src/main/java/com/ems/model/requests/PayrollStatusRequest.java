package com.ems.model.requests;

import com.ems.model.response.PayrollEmployee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayrollStatusRequest {
    private String status;
    private PayrollEmployee employee;

}

/**
 * {
 * "status": "success",
 * "data": {
 * "id": "1",
 * "employee_name": "Tiger Nixon",
 * "employee_salary": "320800",
 * "employee_age": "61",
 * "profile_image": ""
 * }
 * }
 */
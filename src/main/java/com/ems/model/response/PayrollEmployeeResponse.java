package com.ems.model.response;

import com.ems.constants.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollEmployeeResponse {

    private PayrollStatus status;
    private PayrollEmployee payrollEmployee;
}

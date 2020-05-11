package com.ems.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollEmployeeResponse {

    private String status;

    @JsonAlias("data")
    private PayrollEmployee payrollEmployee;
}

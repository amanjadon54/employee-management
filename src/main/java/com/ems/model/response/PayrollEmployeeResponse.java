package com.ems.model.response;

import com.ems.constants.PayrollStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollEmployeeResponse {

    @Enumerated
    private PayrollStatus status;

    @JsonAlias("data")
    private PayrollEmployee payrollEmployee;
}

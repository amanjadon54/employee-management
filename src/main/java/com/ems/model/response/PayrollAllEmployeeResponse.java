package com.ems.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollAllEmployeeResponse {

    private String status;

    @JsonAlias("data")
    private List<PayrollEmployee> payrollEmployee;

}

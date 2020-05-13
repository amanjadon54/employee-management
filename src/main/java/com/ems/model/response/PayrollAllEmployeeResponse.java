package com.ems.model.response;

import com.ems.constants.PayrollStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollAllEmployeeResponse {

    @Enumerated
    private PayrollStatus status;

    @JsonAlias("data")
    private List<PayrollEmployee> payrollEmployee;

}

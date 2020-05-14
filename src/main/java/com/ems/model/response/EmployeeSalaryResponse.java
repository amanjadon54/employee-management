package com.ems.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryResponse {

    private int id;
    private String name;
    private int age;
    private long salary;

}

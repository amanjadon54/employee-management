package com.ems.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(1)
    private int age;

    @NotNull
    @Min(1)
    private long salary;

    @Override
    public String toString() {
        return "CreatePayrollEmployeeRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

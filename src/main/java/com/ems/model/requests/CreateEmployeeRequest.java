package com.ems.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Min(18)
    @Max(100)
    private int age;

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
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

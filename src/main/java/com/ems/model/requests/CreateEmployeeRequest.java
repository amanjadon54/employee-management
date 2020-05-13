package com.ems.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

import static com.ems.constants.StringConstants.VALID_NAME_REGEX;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    @NotNull
    @NotBlank
    @Length(min = 1, max = 50)
    @Pattern(regexp = VALID_NAME_REGEX)
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

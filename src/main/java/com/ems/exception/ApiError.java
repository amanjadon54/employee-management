package com.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 6916217858899431308L;
    private HttpStatus httpStatus;
    private String message;
    private String developerMessage;

}

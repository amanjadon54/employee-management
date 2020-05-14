package com.ems.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PayrollStatus {
    SUCCESS("success"), PENDING("pending"), FAILURE("failure");

    private String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    PayrollStatus(String status) {
        this.status = status;
    }


}

package com.ems.constants;

public enum PayrollStatus {
    SUCCESS("success"), PENDING("pending"), FAILURE("failure");

    private String status;

    public String getStatus() {
        return status;
    }

    PayrollStatus(String status) {
        this.status = status;
    }


}

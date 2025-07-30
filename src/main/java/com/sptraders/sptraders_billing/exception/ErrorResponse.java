package com.sptraders.sptraders_billing.exception;

public class ErrorResponse {

    private String status;

    private String message;

    private String time;

    public ErrorResponse() {

    }

    public ErrorResponse(String status, String message, String time) {
        this.status = status;
        this.message = message;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

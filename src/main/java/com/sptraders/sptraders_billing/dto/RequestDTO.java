package com.sptraders.sptraders_billing.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RequestDTO {

    private String customerName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date toDate;


    public RequestDTO() {
    }

    public RequestDTO(String customerName, Date fromDate, Date toDate) {
        this.customerName = customerName;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}

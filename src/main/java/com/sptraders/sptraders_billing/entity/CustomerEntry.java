package com.sptraders.sptraders_billing.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customer_entry")
public class CustomerEntry {

    @Column(name = "source")
    private static String source = "KPM";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid")
    private Long eId;
    @Column(name = "entry_date")
    private Date date;
    @Column(name = "awb")
    private String awbNo;
    @Column(name = "customer")
    private String customer;
    @Column(name = "destination")
    private String destination;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "total")
    private Double total;

    public static String getSource() {
        return source;
    }

    public static void setSource(String source) {
        CustomerEntry.source = source;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAwbNo() {
        return awbNo;
    }

    public void setAwbNo(String awbNo) {
        this.awbNo = awbNo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CustomerEntry{" +
                "eId=" + eId +
                ", date=" + date +
                ", awbNo='" + awbNo + '\'' +
                ", customer='" + customer + '\'' +
                ", destination='" + destination + '\'' +
                ", weight=" + weight +
                ", total=" + total +
                '}';
    }
}

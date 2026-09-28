package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomerPaymentEntity {

    @Id
    private int id;

    private int customerInvoiceId;
    private int brokerId;
    private String brokerName;
    private double paymentAmount;
    private String paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String referenceNumber;

    public CustomerPaymentEntity() {
    }

    public CustomerPaymentEntity(
            int customerInvoiceId,
            int brokerId,
            String brokerName,
            double paymentAmount,
            String paymentDate,
            String paymentMethod,
            String paymentStatus,
            String referenceNumber) {

        this.id=id;
        this.customerInvoiceId = customerInvoiceId;
        this.brokerId = brokerId;
        this.brokerName = brokerName;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.referenceNumber = referenceNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerInvoiceId() {
        return customerInvoiceId;
    }

    public void setCustomerInvoiceId(int customerInvoiceId) {
        this.customerInvoiceId = customerInvoiceId;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
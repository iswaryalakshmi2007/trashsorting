package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BankPaymentEntity {

    @Id
    private int id;

    private int vendorBillId;
    private String vendorName;
    private double paymentAmount;
    private String paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String referenceNumber;

    public BankPaymentEntity() {
    }

    public BankPaymentEntity(int vendorBillId,
                             String vendorName,
                             double paymentAmount,
                             String paymentDate,
                             String paymentMethod,
                             String paymentStatus,
                             String referenceNumber) {

        this.id=id;
        this.vendorBillId = vendorBillId;
        this.vendorName = vendorName;
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

    public int getVendorBillId() {
        return vendorBillId;
    }

    public void setVendorBillId(int vendorBillId) {
        this.vendorBillId = vendorBillId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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
package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VendorBillEntity {

    @Id
    private int id;

    private int purchaseOrderId;
    private String vendorName;
    private String productName;
    private double billAmount;
    private String billDate;
    private String paymentStatus;

    public VendorBillEntity() {
    }

    public VendorBillEntity(int purchaseOrderId,
                            String vendorName,
                            String productName,
                            double billAmount,
                            String billDate,
                            String paymentStatus) {


        this.id=id;
        this.purchaseOrderId = purchaseOrderId;
        this.vendorName = vendorName;
        this.productName = productName;
        this.billAmount = billAmount;
        this.billDate = billDate;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
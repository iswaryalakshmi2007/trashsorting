package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomerInvoiceEntity {

    @Id
    private int id;

    private int salesOrderId;
    private int brokerId;
    private String brokerName;
    private String productName;
    private String materialType;
    private double deliveredWeight;
    private String unit;
    private double unitPrice;
    private double invoiceAmount;
    private String invoiceDate;
    private String paymentStatus;

    public CustomerInvoiceEntity() {
    }

    public CustomerInvoiceEntity(
            int salesOrderId,
            int brokerId,
            String brokerName,
            String productName,
            String materialType,
            double deliveredWeight,
            String unit,
            double unitPrice,
            double invoiceAmount,
            String invoiceDate,
            String paymentStatus) {

        this.id=id;
        this.salesOrderId = salesOrderId;
        this.brokerId = brokerId;
        this.brokerName = brokerName;
        this.productName = productName;
        this.materialType = materialType;
        this.deliveredWeight = deliveredWeight;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.invoiceAmount = invoiceAmount;
        this.invoiceDate = invoiceDate;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public double getDeliveredWeight() {
        return deliveredWeight;
    }

    public void setDeliveredWeight(double deliveredWeight) {
        this.deliveredWeight = deliveredWeight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
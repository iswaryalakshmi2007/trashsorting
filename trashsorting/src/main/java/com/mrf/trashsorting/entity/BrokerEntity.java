package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BrokerEntity {

    @Id
    private int id;

    private String brokerName;
    private String companyName;
    private String email;
    private String phone;
    private String materialType;

    public BrokerEntity() {
    }

    public BrokerEntity(String brokerName,
                        String companyName,
                        String email,
                        String phone,
                        String materialType) {

        this.id=id;
        this.brokerName = brokerName;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.materialType = materialType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
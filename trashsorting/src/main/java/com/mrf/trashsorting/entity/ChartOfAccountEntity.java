package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChartOfAccountEntity {

    @Id
    private int id;

    private String accountCode;
    private String accountName;
    private String accountType;
    private String description;
    private double openingBalance;

    public ChartOfAccountEntity() {
    }

    public ChartOfAccountEntity(
            String accountCode,
            String accountName,
            String accountType,
            String description,
            double openingBalance) {

        this.id=id;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.accountType = accountType;
        this.description = description;
        this.openingBalance = openingBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
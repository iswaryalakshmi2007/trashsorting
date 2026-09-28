package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JournalEntryEntity {

    @Id
    private int id;

    private String journalType;
    private String transactionDate;
    private String referenceNumber;

    private int debitAccountId;
    private String debitAccountName;
    private double debitAmount;

    private int creditAccountId;
    private String creditAccountName;
    private double creditAmount;

    private String description;

    public JournalEntryEntity() {
    }

    public JournalEntryEntity(
            String journalType,
            String transactionDate,
            String referenceNumber,
            int debitAccountId,
            String debitAccountName,
            double debitAmount,
            int creditAccountId,
            String creditAccountName,
            double creditAmount,
            String description) {

        this.id=id;
        this.journalType = journalType;
        this.transactionDate = transactionDate;
        this.referenceNumber = referenceNumber;
        this.debitAccountId = debitAccountId;
        this.debitAccountName = debitAccountName;
        this.debitAmount = debitAmount;
        this.creditAccountId = creditAccountId;
        this.creditAccountName = creditAccountName;
        this.creditAmount = creditAmount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(int debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public String getDebitAccountName() {
        return debitAccountName;
    }

    public void setDebitAccountName(String debitAccountName) {
        this.debitAccountName = debitAccountName;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public int getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(int creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public String getCreditAccountName() {
        return creditAccountName;
    }

    public void setCreditAccountName(String creditAccountName) {
        this.creditAccountName = creditAccountName;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
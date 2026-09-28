package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SorterEntity {

    @Id
    private int id;

    private String sorterName;
    private String vendorName;
    private String sortingType;
    private String status;

    // Default constructor
    public SorterEntity() {
    }

    // Constructor - DO NOT include id
    public SorterEntity(String sorterName,
                        String vendorName,
                        String sortingType,
                        String status) {

        this.id=id;
        this.sorterName = sorterName;
        this.vendorName = vendorName;
        this.sortingType = sortingType;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSorterName() {
        return sorterName;
    }

    public void setSorterName(String sorterName) {
        this.sorterName = sorterName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
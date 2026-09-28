package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductionEntity {

    @Id
    private int id;

    private String materialType;
    private double sortedWeight;
    private String unit;
    private String sorterName;
    private String productionDate;

    public ProductionEntity() {
    }

    public ProductionEntity(String materialType,
                            double sortedWeight,
                            String unit,
                            String sorterName,
                            String productionDate) {

        this.id=id;
        this.materialType = materialType;
        this.sortedWeight = sortedWeight;
        this.unit = unit;
        this.sorterName = sorterName;
        this.productionDate = productionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public double getSortedWeight() {
        return sortedWeight;
    }

    public void setSortedWeight(double sortedWeight) {
        this.sortedWeight = sortedWeight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSorterName() {
        return sorterName;
    }

    public void setSorterName(String sorterName) {
        this.sorterName = sorterName;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
}
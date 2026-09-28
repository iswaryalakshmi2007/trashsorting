package com.mrf.trashsorting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BudgetEntity {

    @Id
    private int id;

    private int analyticAccountId;

    private String budgetName;

    private String budgetPeriod;

    private double plannedRevenue;

    private double plannedElectricityCost;

    private double plannedMaintenanceCost;

    private double plannedTotalExpense;

    public BudgetEntity() {
    }

    public BudgetEntity(
            int analyticAccountId,
            String budgetName,
            String budgetPeriod,
            double plannedRevenue,
            double plannedElectricityCost,
            double plannedMaintenanceCost,
            double plannedTotalExpense) {

        this.id=id;
        this.analyticAccountId = analyticAccountId;
        this.budgetName = budgetName;
        this.budgetPeriod = budgetPeriod;
        this.plannedRevenue = plannedRevenue;
        this.plannedElectricityCost = plannedElectricityCost;
        this.plannedMaintenanceCost = plannedMaintenanceCost;
        this.plannedTotalExpense = plannedTotalExpense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnalyticAccountId() {
        return analyticAccountId;
    }

    public void setAnalyticAccountId(int analyticAccountId) {
        this.analyticAccountId = analyticAccountId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public String getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(String budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public double getPlannedRevenue() {
        return plannedRevenue;
    }

    public void setPlannedRevenue(double plannedRevenue) {
        this.plannedRevenue = plannedRevenue;
    }

    public double getPlannedElectricityCost() {
        return plannedElectricityCost;
    }

    public void setPlannedElectricityCost(
            double plannedElectricityCost) {

        this.plannedElectricityCost =
                plannedElectricityCost;
    }

    public double getPlannedMaintenanceCost() {
        return plannedMaintenanceCost;
    }

    public void setPlannedMaintenanceCost(
            double plannedMaintenanceCost) {

        this.plannedMaintenanceCost =
                plannedMaintenanceCost;
    }

    public double getPlannedTotalExpense() {
        return plannedTotalExpense;
    }

    public void setPlannedTotalExpense(
            double plannedTotalExpense) {

        this.plannedTotalExpense =
                plannedTotalExpense;
    }
}
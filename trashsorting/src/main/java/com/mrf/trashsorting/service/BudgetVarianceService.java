package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.BudgetEntity;
import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.repository.BudgetRepository;
import com.mrf.trashsorting.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BudgetVarianceService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    public Map<String, Object> generateVarianceReport(
            int budgetId) {

        BudgetEntity budget =
                budgetRepository.findById(budgetId)
                        .orElse(null);

        Map<String, Object> report =
                new HashMap<>();

        if (budget == null) {

            report.put(
                    "error",
                    "Budget not found"
            );

            return report;
        }

        List<LedgerEntity> ledgerEntries =
                ledgerRepository.findAll();

        double actualRevenue = 0.0;
        double actualElectricity = 0.0;
        double actualMaintenance = 0.0;

        for (LedgerEntity entry : ledgerEntries) {

            String accountType =
                    entry.getAccountType();

            String accountName =
                    entry.getAccountName();

            double debit =
                    entry.getDebitAmount();

            double credit =
                    entry.getCreditAmount();

            // Actual Revenue
            if ("INCOME".equalsIgnoreCase(accountType)) {

                actualRevenue +=
                        credit - debit;
            }

            // Actual Electricity Expense
            if ("Conveyor Electricity Costs"
                    .equalsIgnoreCase(accountName)) {

                actualElectricity +=
                        debit - credit;
            }

            // Actual Maintenance Expense
            if ("Machinery Maintenance Expenses"
                    .equalsIgnoreCase(accountName)) {

                actualMaintenance +=
                        debit - credit;
            }
        }

        double plannedRevenue =
                budget.getPlannedRevenue();

        double plannedElectricity =
                budget.getPlannedElectricityCost();

        double plannedMaintenance =
                budget.getPlannedMaintenanceCost();

        double plannedExpense =
                budget.getPlannedTotalExpense();

        double actualExpense =
                actualElectricity
                        + actualMaintenance;

        double revenueVariance =
                actualRevenue
                        - plannedRevenue;

        double electricityVariance =
                actualElectricity
                        - plannedElectricity;

        double maintenanceVariance =
                actualMaintenance
                        - plannedMaintenance;

        double expenseVariance =
                actualExpense
                        - plannedExpense;

        double plannedProfit =
                plannedRevenue
                        - plannedExpense;

        double actualProfit =
                actualRevenue
                        - actualExpense;

        double profitVariance =
                actualProfit
                        - plannedProfit;

        report.put(
                "budgetId",
                budget.getId()
        );

        report.put(
                "budgetName",
                budget.getBudgetName()
        );

        report.put(
                "budgetPeriod",
                budget.getBudgetPeriod()
        );

        report.put(
                "plannedRevenue",
                plannedRevenue
        );

        report.put(
                "actualRevenue",
                actualRevenue
        );

        report.put(
                "revenueVariance",
                revenueVariance
        );

        report.put(
                "plannedElectricityCost",
                plannedElectricity
        );

        report.put(
                "actualElectricityCost",
                actualElectricity
        );

        report.put(
                "electricityVariance",
                electricityVariance
        );

        report.put(
                "plannedMaintenanceCost",
                plannedMaintenance
        );

        report.put(
                "actualMaintenanceCost",
                actualMaintenance
        );

        report.put(
                "maintenanceVariance",
                maintenanceVariance
        );

        report.put(
                "plannedTotalExpense",
                plannedExpense
        );

        report.put(
                "actualTotalExpense",
                actualExpense
        );

        report.put(
                "expenseVariance",
                expenseVariance
        );

        report.put(
                "plannedProfit",
                plannedProfit
        );

        report.put(
                "actualProfit",
                actualProfit
        );

        report.put(
                "profitVariance",
                profitVariance
        );

        return report;
    }
}
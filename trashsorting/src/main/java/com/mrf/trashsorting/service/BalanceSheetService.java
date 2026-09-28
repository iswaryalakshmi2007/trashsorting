package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceSheetService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public Map<String, Object> generateBalanceSheet() {

        List<LedgerEntity> ledgerEntries =
                ledgerRepository.findAll();

        double totalAssets = 0.0;
        double totalLiabilities = 0.0;
        double totalRevenue = 0.0;
        double totalExpenses = 0.0;

        Map<String, Double> assetAccounts =
                new HashMap<>();

        Map<String, Double> liabilityAccounts =
                new HashMap<>();

        for (LedgerEntity entry : ledgerEntries) {

            String accountType =
                    entry.getAccountType();

            String accountName =
                    entry.getAccountName();

            double debit =
                    entry.getDebitAmount();

            double credit =
                    entry.getCreditAmount();

            // =========================
            // ASSET
            // =========================
            if ("ASSET".equalsIgnoreCase(accountType)) {

                double currentBalance =
                        assetAccounts.getOrDefault(
                                accountName,
                                0.0
                        );

                double newBalance =
                        currentBalance
                                + debit
                                - credit;

                assetAccounts.put(
                        accountName,
                        newBalance
                );
            }

            // =========================
            // LIABILITY
            // =========================
            else if ("LIABILITY".equalsIgnoreCase(accountType)) {

                double currentBalance =
                        liabilityAccounts.getOrDefault(
                                accountName,
                                0.0
                        );

                double newBalance =
                        currentBalance
                                + credit
                                - debit;

                liabilityAccounts.put(
                        accountName,
                        newBalance
                );
            }

            // =========================
            // INCOME
            // =========================
            else if ("INCOME".equalsIgnoreCase(accountType)) {

                totalRevenue +=
                        credit - debit;
            }

            // =========================
            // EXPENSE
            // =========================
            else if ("EXPENSE".equalsIgnoreCase(accountType)) {

                totalExpenses +=
                        debit - credit;
            }
        }

        // =========================
        // TOTAL ASSETS
        // =========================
        for (double balance : assetAccounts.values()) {

            totalAssets += balance;
        }

        // =========================
        // TOTAL LIABILITIES
        // =========================
        for (double balance : liabilityAccounts.values()) {

            totalLiabilities += balance;
        }

        // =========================
        // CURRENT PROFIT
        // =========================
        double currentProfit =
                totalRevenue - totalExpenses;

        // =========================
        // LIABILITIES + EQUITY
        // =========================
        double totalLiabilitiesAndEquity =
                totalLiabilities
                        + currentProfit;

        // =========================
        // BALANCE CHECK
        // =========================
        boolean balanced =
                Math.abs(
                        totalAssets
                                - totalLiabilitiesAndEquity
                ) < 0.01;

        // =========================
        // FINAL REPORT
        // =========================
        Map<String, Object> report =
                new HashMap<>();

        report.put(
                "assets",
                assetAccounts
        );

        report.put(
                "liabilities",
                liabilityAccounts
        );

        report.put(
                "totalAssets",
                totalAssets
        );

        report.put(
                "totalLiabilities",
                totalLiabilities
        );

        report.put(
                "currentProfit",
                currentProfit
        );

        report.put(
                "totalLiabilitiesAndEquity",
                totalLiabilitiesAndEquity
        );

        report.put(
                "balanced",
                balanced
        );

        return report;
    }
}
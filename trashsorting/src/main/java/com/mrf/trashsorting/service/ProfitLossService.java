package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfitLossService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public Map<String, Object> generateProfitLossReport() {

        List<LedgerEntity> ledgerEntries =
                ledgerRepository.findAll();

        double totalRevenue = 0.0;
        double totalExpenses = 0.0;

        for (LedgerEntity entry : ledgerEntries) {

            if ("INCOME".equalsIgnoreCase(
                    entry.getAccountType())) {

                totalRevenue +=
                        entry.getCreditAmount()
                                - entry.getDebitAmount();
            }

            if ("EXPENSE".equalsIgnoreCase(
                    entry.getAccountType())) {

                totalExpenses +=
                        entry.getDebitAmount()
                                - entry.getCreditAmount();
            }
        }

        double netProfitLoss =
                totalRevenue - totalExpenses;

        String result;

        if (netProfitLoss >= 0) {
            result = "PROFIT";
        } else {
            result = "LOSS";
        }

        Map<String, Object> report =
                new HashMap<>();

        report.put("totalRevenue", totalRevenue);
        report.put("totalExpenses", totalExpenses);
        report.put("netProfitLoss", netProfitLoss);
        report.put("result", result);

        return report;
    }
}
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.service.BudgetVarianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mrf/reports")
public class BudgetVarianceController {

    @Autowired
    private BudgetVarianceService budgetVarianceService;

    @GetMapping("/budget-variance/{budgetId}")
    public ResponseEntity<Map<String, Object>>
    getBudgetVariance(
            @PathVariable int budgetId) {

        Map<String, Object> report =
                budgetVarianceService
                        .generateVarianceReport(budgetId);

        if (report.containsKey("error")) {

            return new ResponseEntity<>(
                    report,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                report,
                HttpStatus.OK
        );
    }
}
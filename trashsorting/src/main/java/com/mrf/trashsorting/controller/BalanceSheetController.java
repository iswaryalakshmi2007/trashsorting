package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.service.BalanceSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mrf/reports")
public class BalanceSheetController {

    @Autowired
    private BalanceSheetService balanceSheetService;

    @GetMapping("/balance-sheet")
    public ResponseEntity<Map<String, Object>>
    getBalanceSheet() {

        Map<String, Object> report =
                balanceSheetService.generateBalanceSheet();

        return new ResponseEntity<>(
                report,
                HttpStatus.OK
        );
    }
}
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.service.ProfitLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mrf/reports")
public class ProfitLossController {

    @Autowired
    private ProfitLossService profitLossService;

    @GetMapping("/profit-loss")
    public ResponseEntity<Map<String, Object>>
    getProfitLossReport() {

        Map<String, Object> report =
                profitLossService
                        .generateProfitLossReport();

        return new ResponseEntity<>(
                report,
                HttpStatus.OK
        );
    }
}
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping
    public ResponseEntity<LedgerEntity> createLedgerEntry(
            @RequestBody LedgerEntity ledger) {

        LedgerEntity savedLedger =
                ledgerService.createLedgerEntry(ledger);

        return new ResponseEntity<>(
                savedLedger,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<LedgerEntity>>
    getAllLedgerEntries() {

        List<LedgerEntity> ledgers =
                ledgerService.getAllLedgerEntries();

        return new ResponseEntity<>(
                ledgers,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LedgerEntity>
    getLedgerById(@PathVariable int id) {

        LedgerEntity ledger =
                ledgerService.getLedgerById(id);

        if (ledger == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                ledger,
                HttpStatus.OK
        );
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<LedgerEntity>>
    getLedgerByAccountId(
            @PathVariable int accountId) {

        List<LedgerEntity> ledgers =
                ledgerService
                        .getLedgerByAccountId(accountId);

        return new ResponseEntity<>(
                ledgers,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LedgerEntity>
    updateLedgerEntry(
            @PathVariable int id,
            @RequestBody LedgerEntity ledger) {

        LedgerEntity updatedLedger =
                ledgerService.updateLedgerEntry(
                        id,
                        ledger
                );

        if (updatedLedger == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedLedger,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedgerEntry(
            @PathVariable int id) {

        boolean deleted =
                ledgerService.deleteLedgerEntry(id);

        if (!deleted) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
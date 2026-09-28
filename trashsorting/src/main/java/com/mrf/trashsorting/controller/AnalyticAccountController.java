package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.AnalyticAccountEntity;
import com.mrf.trashsorting.service.AnalyticAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/analytic-accounts")
public class AnalyticAccountController {

    @Autowired
    private AnalyticAccountService service;

    @PostMapping
    public ResponseEntity<AnalyticAccountEntity>
    createAccount(
            @RequestBody AnalyticAccountEntity account) {

        AnalyticAccountEntity saved =
                service.createAccount(account);

        return new ResponseEntity<>(
                saved,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<AnalyticAccountEntity>>
    getAllAccounts() {

        return new ResponseEntity<>(
                service.getAllAccounts(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticAccountEntity>
    getAccount(@PathVariable int id) {

        AnalyticAccountEntity account =
                service.getAccount(id);

        if (account == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                account,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalyticAccountEntity>
    updateAccount(
            @PathVariable int id,
            @RequestBody AnalyticAccountEntity account) {

        AnalyticAccountEntity updated =
                service.updateAccount(id, account);

        if (updated == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updated,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteAccount(@PathVariable int id) {

        service.deleteAccount(id);

        return new ResponseEntity<>(
                "Analytic account deleted successfully",
                HttpStatus.OK
        );
    }
}
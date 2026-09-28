package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.ChartOfAccountEntity;
import com.mrf.trashsorting.service.ChartOfAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/accounts")
public class ChartOfAccountController {

    @Autowired
    private ChartOfAccountService chartOfAccountService;

    @PostMapping
    public ResponseEntity<ChartOfAccountEntity> createAccount(
            @RequestBody ChartOfAccountEntity account) {

        ChartOfAccountEntity savedAccount =
                chartOfAccountService.createAccount(account);

        return new ResponseEntity<>(
                savedAccount,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ChartOfAccountEntity>>
    getAllAccounts() {

        List<ChartOfAccountEntity> accounts =
                chartOfAccountService.getAllAccounts();

        return new ResponseEntity<>(
                accounts,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChartOfAccountEntity>
    getAccountById(@PathVariable int id) {

        ChartOfAccountEntity account =
                chartOfAccountService.getAccountById(id);

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
    public ResponseEntity<ChartOfAccountEntity>
    updateAccount(
            @PathVariable int id,
            @RequestBody ChartOfAccountEntity account) {

        ChartOfAccountEntity updatedAccount =
                chartOfAccountService.updateAccount(
                        id,
                        account
                );

        if (updatedAccount == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedAccount,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable int id) {

        boolean deleted =
                chartOfAccountService.deleteAccount(id);

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
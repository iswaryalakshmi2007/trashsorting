package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.BankPaymentEntity;
import com.mrf.trashsorting.service.BankPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/bank-payments")
public class BankPaymentController {

    @Autowired
    private BankPaymentService bankPaymentService;

    // CREATE BANK PAYMENT
    @PostMapping
    public ResponseEntity<BankPaymentEntity> createBankPayment(
            @RequestBody BankPaymentEntity payment) {

        BankPaymentEntity savedPayment =
                bankPaymentService.createBankPayment(payment);

        return new ResponseEntity<>(
                savedPayment,
                HttpStatus.CREATED
        );
    }

    // GET ALL BANK PAYMENTS
    @GetMapping
    public ResponseEntity<List<BankPaymentEntity>>
    getAllBankPayments() {

        List<BankPaymentEntity> payments =
                bankPaymentService.getAllBankPayments();

        return new ResponseEntity<>(
                payments,
                HttpStatus.OK
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BankPaymentEntity>
    getBankPaymentById(@PathVariable int id) {

        BankPaymentEntity payment =
                bankPaymentService.getBankPaymentById(id);

        if (payment == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                payment,
                HttpStatus.OK
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<BankPaymentEntity>
    updateBankPayment(
            @PathVariable int id,
            @RequestBody BankPaymentEntity payment) {

        BankPaymentEntity updatedPayment =
                bankPaymentService.updateBankPayment(
                        id,
                        payment
                );

        if (updatedPayment == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedPayment,
                HttpStatus.OK
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankPayment(
            @PathVariable int id) {

        boolean deleted =
                bankPaymentService.deleteBankPayment(id);

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
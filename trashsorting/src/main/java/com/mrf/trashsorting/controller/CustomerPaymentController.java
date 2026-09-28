package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.CustomerPaymentEntity;
import com.mrf.trashsorting.service.CustomerPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/customer-payments")
public class CustomerPaymentController {

    @Autowired
    private CustomerPaymentService customerPaymentService;

    @PostMapping
    public ResponseEntity<CustomerPaymentEntity> createCustomerPayment(
            @RequestBody CustomerPaymentEntity payment) {

        CustomerPaymentEntity savedPayment =
                customerPaymentService
                        .createCustomerPayment(payment);

        return new ResponseEntity<>(
                savedPayment,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerPaymentEntity>>
    getAllCustomerPayments() {

        List<CustomerPaymentEntity> payments =
                customerPaymentService
                        .getAllCustomerPayments();

        return new ResponseEntity<>(
                payments,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerPaymentEntity>
    getCustomerPaymentById(
            @PathVariable int id) {

        CustomerPaymentEntity payment =
                customerPaymentService
                        .getCustomerPaymentById(id);

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

    @PutMapping("/{id}")
    public ResponseEntity<CustomerPaymentEntity>
    updateCustomerPayment(
            @PathVariable int id,
            @RequestBody CustomerPaymentEntity payment) {

        CustomerPaymentEntity updatedPayment =
                customerPaymentService
                        .updateCustomerPayment(id, payment);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerPayment(
            @PathVariable int id) {

        boolean deleted =
                customerPaymentService
                        .deleteCustomerPayment(id);

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
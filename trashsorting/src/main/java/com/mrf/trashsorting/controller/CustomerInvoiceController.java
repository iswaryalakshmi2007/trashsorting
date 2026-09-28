package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.CustomerInvoiceEntity;
import com.mrf.trashsorting.service.CustomerInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/customer-invoices")
public class CustomerInvoiceController {

    @Autowired
    private CustomerInvoiceService customerInvoiceService;

    @PostMapping
    public ResponseEntity<CustomerInvoiceEntity> createCustomerInvoice(
            @RequestBody CustomerInvoiceEntity invoice) {

        CustomerInvoiceEntity savedInvoice =
                customerInvoiceService
                        .createCustomerInvoice(invoice);

        return new ResponseEntity<>(
                savedInvoice,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerInvoiceEntity>>
    getAllCustomerInvoices() {

        List<CustomerInvoiceEntity> invoices =
                customerInvoiceService
                        .getAllCustomerInvoices();

        return new ResponseEntity<>(
                invoices,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInvoiceEntity>
    getCustomerInvoiceById(
            @PathVariable int id) {

        CustomerInvoiceEntity invoice =
                customerInvoiceService
                        .getCustomerInvoiceById(id);

        if (invoice == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                invoice,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerInvoiceEntity>
    updateCustomerInvoice(
            @PathVariable int id,
            @RequestBody CustomerInvoiceEntity invoice) {

        CustomerInvoiceEntity updatedInvoice =
                customerInvoiceService
                        .updateCustomerInvoice(id, invoice);

        if (updatedInvoice == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedInvoice,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerInvoice(
            @PathVariable int id) {

        boolean deleted =
                customerInvoiceService
                        .deleteCustomerInvoice(id);

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
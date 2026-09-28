package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.VendorBillEntity;
import com.mrf.trashsorting.service.VendorBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/vendor-bills")
public class VendorBillController {

    @Autowired
    private VendorBillService vendorBillService;

    // CREATE VENDOR BILL
    @PostMapping
    public ResponseEntity<VendorBillEntity> createVendorBill(
            @RequestBody VendorBillEntity vendorBill) {

        VendorBillEntity savedBill =
                vendorBillService.createVendorBill(vendorBill);

        return new ResponseEntity<>(
                savedBill,
                HttpStatus.CREATED
        );
    }

    // GET ALL VENDOR BILLS
    @GetMapping
    public ResponseEntity<List<VendorBillEntity>>
    getAllVendorBills() {

        List<VendorBillEntity> bills =
                vendorBillService.getAllVendorBills();

        return new ResponseEntity<>(
                bills,
                HttpStatus.OK
        );
    }

    // GET VENDOR BILL BY ID
    @GetMapping("/{id}")
    public ResponseEntity<VendorBillEntity>
    getVendorBillById(@PathVariable int id) {

        VendorBillEntity bill =
                vendorBillService.getVendorBillById(id);

        if (bill == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                bill,
                HttpStatus.OK
        );
    }

    // UPDATE VENDOR BILL
    @PutMapping("/{id}")
    public ResponseEntity<VendorBillEntity>
    updateVendorBill(
            @PathVariable int id,
            @RequestBody VendorBillEntity vendorBill) {

        VendorBillEntity updatedBill =
                vendorBillService.updateVendorBill(
                        id,
                        vendorBill
                );

        if (updatedBill == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedBill,
                HttpStatus.OK
        );
    }

    // DELETE VENDOR BILL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorBill(
            @PathVariable int id) {

        boolean deleted =
                vendorBillService.deleteVendorBill(id);

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
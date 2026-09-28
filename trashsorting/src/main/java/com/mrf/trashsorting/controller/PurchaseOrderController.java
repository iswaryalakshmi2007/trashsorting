package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.PurchaseOrderEntity;
import com.mrf.trashsorting.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // CREATE PURCHASE ORDER
    @PostMapping
    public ResponseEntity<PurchaseOrderEntity> createPurchaseOrder(
            @RequestBody PurchaseOrderEntity purchaseOrder) {

        PurchaseOrderEntity savedOrder =
                purchaseOrderService
                        .createPurchaseOrder(purchaseOrder);

        return new ResponseEntity<>(
                savedOrder,
                HttpStatus.CREATED
        );
    }

    // GET ALL PURCHASE ORDERS
    @GetMapping
    public ResponseEntity<List<PurchaseOrderEntity>>
    getAllPurchaseOrders() {

        List<PurchaseOrderEntity> orders =
                purchaseOrderService
                        .getAllPurchaseOrders();

        return new ResponseEntity<>(
                orders,
                HttpStatus.OK
        );
    }

    // GET PURCHASE ORDER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderEntity>
    getPurchaseOrderById(@PathVariable int id) {

        PurchaseOrderEntity order =
                purchaseOrderService
                        .getPurchaseOrderById(id);

        if (order == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                order,
                HttpStatus.OK
        );
    }

    // UPDATE PURCHASE ORDER
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderEntity>
    updatePurchaseOrder(
            @PathVariable int id,
            @RequestBody PurchaseOrderEntity purchaseOrder) {

        PurchaseOrderEntity updatedOrder =
                purchaseOrderService
                        .updatePurchaseOrder(
                                id,
                                purchaseOrder
                        );

        if (updatedOrder == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedOrder,
                HttpStatus.OK
        );
    }

    // DELETE PURCHASE ORDER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(
            @PathVariable int id) {

        boolean deleted =
                purchaseOrderService
                        .deletePurchaseOrder(id);

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
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.SalesOrderEntity;
import com.mrf.trashsorting.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/sales-orders")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    // CREATE SALES ORDER
    @PostMapping
    public ResponseEntity<SalesOrderEntity> createSalesOrder(
            @RequestBody SalesOrderEntity salesOrder) {

        SalesOrderEntity savedOrder =
                salesOrderService.createSalesOrder(salesOrder);

        return new ResponseEntity<>(
                savedOrder,
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<SalesOrderEntity>>
    getAllSalesOrders() {

        List<SalesOrderEntity> orders =
                salesOrderService.getAllSalesOrders();

        return new ResponseEntity<>(
                orders,
                HttpStatus.OK
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderEntity>
    getSalesOrderById(@PathVariable int id) {

        SalesOrderEntity order =
                salesOrderService.getSalesOrderById(id);

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

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderEntity>
    updateSalesOrder(
            @PathVariable int id,
            @RequestBody SalesOrderEntity salesOrder) {

        SalesOrderEntity updatedOrder =
                salesOrderService.updateSalesOrder(
                        id,
                        salesOrder
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

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesOrder(
            @PathVariable int id) {

        boolean deleted =
                salesOrderService.deleteSalesOrder(id);

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
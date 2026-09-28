package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.BrokerEntity;
import com.mrf.trashsorting.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/brokers")
public class BrokerController {

    @Autowired
    private BrokerService brokerService;

    // CREATE BROKER
    @PostMapping
    public ResponseEntity<BrokerEntity> createBroker(
            @RequestBody BrokerEntity broker) {

        BrokerEntity savedBroker =
                brokerService.createBroker(broker);

        return new ResponseEntity<>(
                savedBroker,
                HttpStatus.CREATED
        );
    }

    // GET ALL BROKERS
    @GetMapping
    public ResponseEntity<List<BrokerEntity>> getAllBrokers() {

        List<BrokerEntity> brokers =
                brokerService.getAllBrokers();

        return new ResponseEntity<>(
                brokers,
                HttpStatus.OK
        );
    }

    // GET BROKER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BrokerEntity> getBrokerById(
            @PathVariable int id) {

        BrokerEntity broker =
                brokerService.getBrokerById(id);

        if (broker == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                broker,
                HttpStatus.OK
        );
    }

    // UPDATE BROKER
    @PutMapping("/{id}")
    public ResponseEntity<BrokerEntity> updateBroker(
            @PathVariable int id,
            @RequestBody BrokerEntity broker) {

        BrokerEntity updatedBroker =
                brokerService.updateBroker(id, broker);

        if (updatedBroker == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedBroker,
                HttpStatus.OK
        );
    }

    // DELETE BROKER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBroker(
            @PathVariable int id) {

        boolean deleted =
                brokerService.deleteBroker(id);

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
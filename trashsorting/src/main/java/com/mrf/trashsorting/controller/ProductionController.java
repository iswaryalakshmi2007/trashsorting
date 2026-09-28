package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.ProductionEntity;
import com.mrf.trashsorting.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/production")
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    // CREATE PRODUCTION
    @PostMapping
    public ResponseEntity<ProductionEntity> createProduction(
            @RequestBody ProductionEntity production) {

        ProductionEntity savedProduction =
                productionService.createProduction(production);

        return new ResponseEntity<>(
                savedProduction,
                HttpStatus.CREATED
        );
    }

    // GET ALL PRODUCTION
    @GetMapping
    public ResponseEntity<List<ProductionEntity>> getAllProductions() {

        List<ProductionEntity> productions =
                productionService.getAllProductions();

        return new ResponseEntity<>(
                productions,
                HttpStatus.OK
        );
    }

    // GET PRODUCTION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductionEntity> getProductionById(
            @PathVariable int id) {

        ProductionEntity production =
                productionService.getProductionById(id);

        if (production == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                production,
                HttpStatus.OK
        );
    }

    // UPDATE PRODUCTION
    @PutMapping("/{id}")
    public ResponseEntity<ProductionEntity> updateProduction(
            @PathVariable int id,
            @RequestBody ProductionEntity production) {

        ProductionEntity updatedProduction =
                productionService.updateProduction(
                        id,
                        production
                );

        if (updatedProduction == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedProduction,
                HttpStatus.OK
        );
    }

    // DELETE PRODUCTION
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduction(
            @PathVariable int id) {

        boolean deleted =
                productionService.deleteProduction(id);

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
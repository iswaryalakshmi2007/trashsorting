package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.InventoryEntity;
import com.mrf.trashsorting.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // CREATE INVENTORY
    @PostMapping
    public ResponseEntity<InventoryEntity> createInventory(
            @RequestBody InventoryEntity inventory) {

        InventoryEntity savedInventory =
                inventoryService.createInventory(inventory);

        return new ResponseEntity<>(
                savedInventory,
                HttpStatus.CREATED
        );
    }

    // GET ALL INVENTORY
    @GetMapping
    public ResponseEntity<List<InventoryEntity>> getAllInventory() {

        List<InventoryEntity> inventory =
                inventoryService.getAllInventory();

        return new ResponseEntity<>(
                inventory,
                HttpStatus.OK
        );
    }

    // GET INVENTORY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryEntity> getInventoryById(
            @PathVariable int id) {

        InventoryEntity inventory =
                inventoryService.getInventoryById(id);

        if (inventory == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                inventory,
                HttpStatus.OK
        );
    }

    // UPDATE INVENTORY
    @PutMapping("/{id}")
    public ResponseEntity<InventoryEntity> updateInventory(
            @PathVariable int id,
            @RequestBody InventoryEntity inventory) {

        InventoryEntity updatedInventory =
                inventoryService.updateInventory(
                        id,
                        inventory
                );

        if (updatedInventory == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedInventory,
                HttpStatus.OK
        );
    }

    // DELETE INVENTORY
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable int id) {

        boolean deleted =
                inventoryService.deleteInventory(id);

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
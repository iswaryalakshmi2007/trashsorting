package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.InventoryEntity;
import com.mrf.trashsorting.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // CREATE
    public InventoryEntity createInventory(
            InventoryEntity inventory) {

        return inventoryRepository.save(inventory);
    }

    // GET ALL
    public List<InventoryEntity> getAllInventory() {

        return inventoryRepository.findAll();
    }

    // GET BY ID
    public InventoryEntity getInventoryById(int id) {

        return inventoryRepository.findById(id).orElse(null);
    }

    // UPDATE
    public InventoryEntity updateInventory(
            int id,
            InventoryEntity inventory) {

        InventoryEntity existingInventory =
                inventoryRepository.findById(id).orElse(null);

        if (existingInventory == null) {
            return null;
        }

        existingInventory.setMaterialType(
                inventory.getMaterialType());

        existingInventory.setQuantity(
                inventory.getQuantity());

        existingInventory.setUnit(
                inventory.getUnit());

        existingInventory.setUnitPrice(
                inventory.getUnitPrice());

        return inventoryRepository.save(existingInventory);
    }

    // DELETE
    public boolean deleteInventory(int id) {

        if (!inventoryRepository.existsById(id)) {
            return false;
        }

        inventoryRepository.deleteById(id);
        return true;
    }
}
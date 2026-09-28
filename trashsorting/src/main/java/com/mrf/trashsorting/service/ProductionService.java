package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.InventoryEntity;
import com.mrf.trashsorting.entity.ProductionEntity;
import com.mrf.trashsorting.repository.InventoryRepository;
import com.mrf.trashsorting.repository.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionService {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    // CREATE PRODUCTION + UPDATE INVENTORY
    public ProductionEntity createProduction(
            ProductionEntity production) {

        // 1. Save Production Record
        ProductionEntity savedProduction =
                productionRepository.save(production);

        // 2. Find existing inventory for same material
        List<InventoryEntity> inventories =
                inventoryRepository.findAll();

        InventoryEntity matchingInventory = null;

        for (InventoryEntity inventory : inventories) {

            if (inventory.getMaterialType()
                    .equalsIgnoreCase(
                            production.getMaterialType())) {

                matchingInventory = inventory;
                break;
            }
        }

        // 3. Update existing inventory
        if (matchingInventory != null) {

            matchingInventory.setQuantity(
                    matchingInventory.getQuantity()
                            + production.getSortedWeight());

            inventoryRepository.save(matchingInventory);

        } else {

            // 4. If inventory doesn't exist,
            // create a new inventory record

            InventoryEntity newInventory =
                    new InventoryEntity();

            newInventory.setMaterialType(
                    production.getMaterialType());

            newInventory.setQuantity(
                    production.getSortedWeight());

            newInventory.setUnit(
                    production.getUnit());

            newInventory.setUnitPrice(0.0);

            inventoryRepository.save(newInventory);
        }

        return savedProduction;
    }


    // GET ALL PRODUCTION RECORDS
    public List<ProductionEntity> getAllProductions() {

        return productionRepository.findAll();
    }


    // GET PRODUCTION BY ID
    public ProductionEntity getProductionById(int id) {

        return productionRepository
                .findById(id)
                .orElse(null);
    }


    // UPDATE PRODUCTION
    public ProductionEntity updateProduction(
            int id,
            ProductionEntity production) {

        ProductionEntity existingProduction =
                productionRepository
                        .findById(id)
                        .orElse(null);

        if (existingProduction == null) {
            return null;
        }

        existingProduction.setMaterialType(
                production.getMaterialType());

        existingProduction.setSortedWeight(
                production.getSortedWeight());

        existingProduction.setUnit(
                production.getUnit());

        existingProduction.setSorterName(
                production.getSorterName());

        existingProduction.setProductionDate(
                production.getProductionDate());

        return productionRepository.save(
                existingProduction);
    }


    // DELETE PRODUCTION
    public boolean deleteProduction(int id) {

        if (!productionRepository.existsById(id)) {
            return false;
        }

        productionRepository.deleteById(id);

        return true;
    }
}
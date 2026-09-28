package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository
        extends JpaRepository<InventoryEntity, Integer> {

}
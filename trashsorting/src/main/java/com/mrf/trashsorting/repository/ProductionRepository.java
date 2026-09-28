package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.ProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository
        extends JpaRepository<ProductionEntity, Integer> {

}
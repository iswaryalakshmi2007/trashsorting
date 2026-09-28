package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrderEntity, Integer> {

}
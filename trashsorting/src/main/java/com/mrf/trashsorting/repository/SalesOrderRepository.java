package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository
        extends JpaRepository<SalesOrderEntity, Integer> {

}
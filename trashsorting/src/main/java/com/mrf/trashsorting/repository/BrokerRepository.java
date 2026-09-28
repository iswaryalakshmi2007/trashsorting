package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.BrokerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository
        extends JpaRepository<BrokerEntity, Integer> {

}
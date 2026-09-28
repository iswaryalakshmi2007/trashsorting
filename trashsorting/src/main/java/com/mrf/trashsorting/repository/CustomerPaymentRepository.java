package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.CustomerPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPaymentRepository
        extends JpaRepository<CustomerPaymentEntity, Integer> {
}
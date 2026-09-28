package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.BankPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankPaymentRepository
        extends JpaRepository<BankPaymentEntity, Integer> {

}
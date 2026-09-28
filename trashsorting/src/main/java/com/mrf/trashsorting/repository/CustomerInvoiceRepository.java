package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.CustomerInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInvoiceRepository
        extends JpaRepository<CustomerInvoiceEntity, Integer> {
}
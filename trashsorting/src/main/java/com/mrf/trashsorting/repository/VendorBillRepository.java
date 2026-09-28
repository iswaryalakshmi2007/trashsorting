package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.VendorBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorBillRepository
        extends JpaRepository<VendorBillEntity, Integer> {

}
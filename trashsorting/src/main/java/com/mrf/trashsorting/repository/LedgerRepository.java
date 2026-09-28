package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.LedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository
        extends JpaRepository<LedgerEntity, Integer> {

    List<LedgerEntity> findByAccountId(int accountId);
}
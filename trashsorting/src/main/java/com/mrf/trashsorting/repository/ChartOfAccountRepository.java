package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.ChartOfAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartOfAccountRepository
        extends JpaRepository<ChartOfAccountEntity, Integer> {
}
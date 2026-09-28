package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.AnalyticAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticAccountRepository
        extends JpaRepository<AnalyticAccountEntity, Integer> {
}
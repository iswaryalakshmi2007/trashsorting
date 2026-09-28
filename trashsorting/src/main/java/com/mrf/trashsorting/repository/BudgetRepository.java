package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository
        extends JpaRepository<BudgetEntity, Integer> {
}
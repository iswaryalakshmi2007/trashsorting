package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.SorterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SorterRepository extends JpaRepository<SorterEntity, Integer> {
}
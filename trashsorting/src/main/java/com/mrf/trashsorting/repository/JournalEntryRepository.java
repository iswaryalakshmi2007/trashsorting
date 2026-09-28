package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.JournalEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository
        extends JpaRepository<JournalEntryEntity, Integer> {
}
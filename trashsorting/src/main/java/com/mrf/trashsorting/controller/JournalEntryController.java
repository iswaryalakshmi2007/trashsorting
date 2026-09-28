package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.JournalEntryEntity;
import com.mrf.trashsorting.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/journal-entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<JournalEntryEntity> createJournalEntry(
            @RequestBody JournalEntryEntity journalEntry) {

        JournalEntryEntity savedEntry =
                journalEntryService
                        .createJournalEntry(journalEntry);

        return new ResponseEntity<>(
                savedEntry,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<JournalEntryEntity>>
    getAllJournalEntries() {

        List<JournalEntryEntity> entries =
                journalEntryService
                        .getAllJournalEntries();

        return new ResponseEntity<>(
                entries,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryEntity>
    getJournalEntryById(@PathVariable int id) {

        JournalEntryEntity entry =
                journalEntryService
                        .getJournalEntryById(id);

        if (entry == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                entry,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryEntity>
    updateJournalEntry(
            @PathVariable int id,
            @RequestBody JournalEntryEntity journalEntry) {

        JournalEntryEntity updatedEntry =
                journalEntryService
                        .updateJournalEntry(
                                id,
                                journalEntry
                        );

        if (updatedEntry == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedEntry,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournalEntry(
            @PathVariable int id) {

        boolean deleted =
                journalEntryService
                        .deleteJournalEntry(id);

        if (!deleted) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}
package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.JournalEntryEntity;
import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.repository.JournalEntryRepository;
import com.mrf.trashsorting.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private LedgerRepository ledgerRepository;


    // CREATE JOURNAL ENTRY + AUTOMATIC LEDGER
    public JournalEntryEntity createJournalEntry(
            JournalEntryEntity journalEntry) {

        // Save Journal Entry
        JournalEntryEntity savedEntry =
                journalEntryRepository.save(journalEntry);


        // -----------------------------
        // DEBIT ENTRY → LEDGER
        // -----------------------------

        LedgerEntity debitLedger =
                new LedgerEntity();

        debitLedger.setAccountId(
                savedEntry.getDebitAccountId());

        debitLedger.setAccountName(
                savedEntry.getDebitAccountName());

        debitLedger.setAccountType(
                getAccountType(
                        savedEntry.getDebitAccountName()));

        debitLedger.setTransactionDate(
                savedEntry.getTransactionDate());

        debitLedger.setReferenceNumber(
                savedEntry.getReferenceNumber());

        debitLedger.setDescription(
                savedEntry.getDescription());

        debitLedger.setDebitAmount(
                savedEntry.getDebitAmount());

        debitLedger.setCreditAmount(0.0);

        debitLedger.setBalance(
                savedEntry.getDebitAmount());

        ledgerRepository.save(debitLedger);


        // -----------------------------
        // CREDIT ENTRY → LEDGER
        // -----------------------------

        LedgerEntity creditLedger =
                new LedgerEntity();

        creditLedger.setAccountId(
                savedEntry.getCreditAccountId());

        creditLedger.setAccountName(
                savedEntry.getCreditAccountName());

        creditLedger.setAccountType(
                getAccountType(
                        savedEntry.getCreditAccountName()));

        creditLedger.setTransactionDate(
                savedEntry.getTransactionDate());

        creditLedger.setReferenceNumber(
                savedEntry.getReferenceNumber());

        creditLedger.setDescription(
                savedEntry.getDescription());

        creditLedger.setDebitAmount(0.0);

        creditLedger.setCreditAmount(
                savedEntry.getCreditAmount());

        creditLedger.setBalance(
                savedEntry.getCreditAmount());

        ledgerRepository.save(creditLedger);


        return savedEntry;
    }


    // GET ALL
    public List<JournalEntryEntity> getAllJournalEntries() {

        return journalEntryRepository.findAll();
    }


    // GET BY ID
    public JournalEntryEntity getJournalEntryById(int id) {

        return journalEntryRepository
                .findById(id)
                .orElse(null);
    }


    // UPDATE
    public JournalEntryEntity updateJournalEntry(
            int id,
            JournalEntryEntity journalEntry) {

        JournalEntryEntity existingEntry =
                journalEntryRepository
                        .findById(id)
                        .orElse(null);

        if (existingEntry == null) {
            return null;
        }

        existingEntry.setJournalType(
                journalEntry.getJournalType());

        existingEntry.setTransactionDate(
                journalEntry.getTransactionDate());

        existingEntry.setReferenceNumber(
                journalEntry.getReferenceNumber());

        existingEntry.setDebitAccountId(
                journalEntry.getDebitAccountId());

        existingEntry.setDebitAccountName(
                journalEntry.getDebitAccountName());

        existingEntry.setDebitAmount(
                journalEntry.getDebitAmount());

        existingEntry.setCreditAccountId(
                journalEntry.getCreditAccountId());

        existingEntry.setCreditAccountName(
                journalEntry.getCreditAccountName());

        existingEntry.setCreditAmount(
                journalEntry.getCreditAmount());

        existingEntry.setDescription(
                journalEntry.getDescription());

        return journalEntryRepository.save(existingEntry);
    }


    // DELETE
    public boolean deleteJournalEntry(int id) {

        if (!journalEntryRepository.existsById(id)) {
            return false;
        }

        journalEntryRepository.deleteById(id);

        return true;
    }


    // ACCOUNT TYPE
    private String getAccountType(String accountName) {

        if ("Cash/Bank".equalsIgnoreCase(accountName)) {
            return "ASSET";
        }

        if ("Baled Inventory".equalsIgnoreCase(accountName)) {
            return "ASSET";
        }

        if ("Optical Sorting Machinery"
                .equalsIgnoreCase(accountName)) {
            return "ASSET";
        }

        if ("Sorter Manufacturer Payables"
                .equalsIgnoreCase(accountName)) {
            return "LIABILITY";
        }

        if ("Power Utility Creditors"
                .equalsIgnoreCase(accountName)) {
            return "LIABILITY";
        }

        if ("Recyclable Material Sales Revenue"
                .equalsIgnoreCase(accountName)) {
            return "INCOME";
        }

        if ("Machinery Maintenance Expenses"
                .equalsIgnoreCase(accountName)) {
            return "EXPENSE";
        }

        if ("Conveyor Electricity Costs"
                .equalsIgnoreCase(accountName)) {
            return "EXPENSE";
        }

        return "OTHER";
    }
}
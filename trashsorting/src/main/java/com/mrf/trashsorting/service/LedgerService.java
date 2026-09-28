package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.LedgerEntity;
import com.mrf.trashsorting.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public LedgerEntity createLedgerEntry(
            LedgerEntity ledger) {

        return ledgerRepository.save(ledger);
    }

    public List<LedgerEntity> getAllLedgerEntries() {

        return ledgerRepository.findAll();
    }

    public LedgerEntity getLedgerById(int id) {

        return ledgerRepository
                .findById(id)
                .orElse(null);
    }

    public List<LedgerEntity> getLedgerByAccountId(
            int accountId) {

        return ledgerRepository
                .findByAccountId(accountId);
    }

    public LedgerEntity updateLedgerEntry(
            int id,
            LedgerEntity ledger) {

        LedgerEntity existingLedger =
                ledgerRepository
                        .findById(id)
                        .orElse(null);

        if (existingLedger == null) {
            return null;
        }

        existingLedger.setAccountId(
                ledger.getAccountId());

        existingLedger.setAccountName(
                ledger.getAccountName());

        existingLedger.setAccountType(
                ledger.getAccountType());

        existingLedger.setTransactionDate(
                ledger.getTransactionDate());

        existingLedger.setReferenceNumber(
                ledger.getReferenceNumber());

        existingLedger.setDescription(
                ledger.getDescription());

        existingLedger.setDebitAmount(
                ledger.getDebitAmount());

        existingLedger.setCreditAmount(
                ledger.getCreditAmount());

        existingLedger.setBalance(
                ledger.getBalance());

        return ledgerRepository.save(existingLedger);
    }

    public boolean deleteLedgerEntry(int id) {

        if (!ledgerRepository.existsById(id)) {
            return false;
        }

        ledgerRepository.deleteById(id);
        return true;
    }
}
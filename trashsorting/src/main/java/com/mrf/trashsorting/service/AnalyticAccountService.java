package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.AnalyticAccountEntity;
import com.mrf.trashsorting.repository.AnalyticAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticAccountService {

    @Autowired
    private AnalyticAccountRepository repository;

    public AnalyticAccountEntity createAccount(
            AnalyticAccountEntity account) {

        return repository.save(account);
    }

    public List<AnalyticAccountEntity> getAllAccounts() {

        return repository.findAll();
    }

    public AnalyticAccountEntity getAccount(int id) {

        return repository.findById(id)
                .orElse(null);
    }

    public AnalyticAccountEntity updateAccount(
            int id,
            AnalyticAccountEntity account) {

        AnalyticAccountEntity existing =
                repository.findById(id)
                        .orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setAccountName(
                account.getAccountName()
        );

        existing.setCostCenter(
                account.getCostCenter()
        );

        existing.setFacilityName(
                account.getFacilityName()
        );

        existing.setDescription(
                account.getDescription()
        );

        existing.setStatus(
                account.getStatus()
        );

        return repository.save(existing);
    }

    public void deleteAccount(int id) {

        repository.deleteById(id);
    }
}
package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.ChartOfAccountEntity;
import com.mrf.trashsorting.repository.ChartOfAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartOfAccountService {

    @Autowired
    private ChartOfAccountRepository chartOfAccountRepository;

    public ChartOfAccountEntity createAccount(
            ChartOfAccountEntity account) {

        return chartOfAccountRepository.save(account);
    }

    public List<ChartOfAccountEntity> getAllAccounts() {

        return chartOfAccountRepository.findAll();
    }

    public ChartOfAccountEntity getAccountById(int id) {

        return chartOfAccountRepository
                .findById(id)
                .orElse(null);
    }

    public ChartOfAccountEntity updateAccount(
            int id,
            ChartOfAccountEntity account) {

        ChartOfAccountEntity existingAccount =
                chartOfAccountRepository
                        .findById(id)
                        .orElse(null);

        if (existingAccount == null) {
            return null;
        }

        existingAccount.setAccountCode(
                account.getAccountCode());

        existingAccount.setAccountName(
                account.getAccountName());

        existingAccount.setAccountType(
                account.getAccountType());

        existingAccount.setDescription(
                account.getDescription());

        existingAccount.setOpeningBalance(
                account.getOpeningBalance());

        return chartOfAccountRepository.save(existingAccount);
    }

    public boolean deleteAccount(int id) {

        if (!chartOfAccountRepository.existsById(id)) {
            return false;
        }

        chartOfAccountRepository.deleteById(id);
        return true;
    }
}
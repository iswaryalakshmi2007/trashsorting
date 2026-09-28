package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.BudgetEntity;
import com.mrf.trashsorting.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository repository;

    public BudgetEntity createBudget(
            BudgetEntity budget) {

        return repository.save(budget);
    }

    public List<BudgetEntity> getAllBudgets() {

        return repository.findAll();
    }

    public BudgetEntity getBudget(int id) {

        return repository.findById(id)
                .orElse(null);
    }

    public BudgetEntity updateBudget(
            int id,
            BudgetEntity budget) {

        BudgetEntity existing =
                repository.findById(id)
                        .orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setAnalyticAccountId(
                budget.getAnalyticAccountId()
        );

        existing.setBudgetName(
                budget.getBudgetName()
        );

        existing.setBudgetPeriod(
                budget.getBudgetPeriod()
        );

        existing.setPlannedRevenue(
                budget.getPlannedRevenue()
        );

        existing.setPlannedElectricityCost(
                budget.getPlannedElectricityCost()
        );

        existing.setPlannedMaintenanceCost(
                budget.getPlannedMaintenanceCost()
        );

        existing.setPlannedTotalExpense(
                budget.getPlannedTotalExpense()
        );

        return repository.save(existing);
    }

    public void deleteBudget(int id) {

        repository.deleteById(id);
    }
}
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.BudgetEntity;
import com.mrf.trashsorting.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/budgets")
public class BudgetController {

    @Autowired
    private BudgetService service;

    @PostMapping
    public ResponseEntity<BudgetEntity>
    createBudget(
            @RequestBody BudgetEntity budget) {

        BudgetEntity saved =
                service.createBudget(budget);

        return new ResponseEntity<>(
                saved,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<BudgetEntity>>
    getAllBudgets() {

        return new ResponseEntity<>(
                service.getAllBudgets(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetEntity>
    getBudget(@PathVariable int id) {

        BudgetEntity budget =
                service.getBudget(id);

        if (budget == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                budget,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetEntity>
    updateBudget(
            @PathVariable int id,
            @RequestBody BudgetEntity budget) {

        BudgetEntity updated =
                service.updateBudget(id, budget);

        if (updated == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updated,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteBudget(@PathVariable int id) {

        service.deleteBudget(id);

        return new ResponseEntity<>(
                "Budget deleted successfully",
                HttpStatus.OK
        );
    }
}
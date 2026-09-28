package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.SorterEntity;
import com.mrf.trashsorting.service.SorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/sorters")
public class SorterController {

    @Autowired
    private SorterService sorterService;

    @PostMapping
    public ResponseEntity<SorterEntity> createSorter(
            @RequestBody SorterEntity sorter) {

        SorterEntity savedSorter =
                sorterService.createSorter(sorter);

        return new ResponseEntity<>(
                savedSorter,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<SorterEntity>> getAllSorters() {

        List<SorterEntity> sorters =
                sorterService.getAllSorters();

        return new ResponseEntity<>(
                sorters,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SorterEntity> getSorterById(
            @PathVariable int id) {

        SorterEntity sorter =
                sorterService.getSorterById(id);

        if (sorter == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                sorter,
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SorterEntity> updateSorter(
            @PathVariable int id,
            @RequestBody SorterEntity sorter) {

        SorterEntity updatedSorter =
                sorterService.updateSorter(id, sorter);

        if (updatedSorter == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedSorter,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSorter(
            @PathVariable int id) {

        boolean deleted =
                sorterService.deleteSorter(id);

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
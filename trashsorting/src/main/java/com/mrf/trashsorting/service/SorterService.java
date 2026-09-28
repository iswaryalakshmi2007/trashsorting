package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.SorterEntity;
import com.mrf.trashsorting.repository.SorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SorterService {

    @Autowired
    private SorterRepository sorterRepository;

    // Create Sorter
    public SorterEntity createSorter(SorterEntity sorter) {
        return sorterRepository.save(sorter);
    }

    // Get All Sorters
    public List<SorterEntity> getAllSorters() {
        return sorterRepository.findAll();
    }

    // Get Sorter By ID
    public SorterEntity getSorterById(int id) {
        return sorterRepository.findById(id).orElse(null);
    }

    // Update Sorter
    public SorterEntity updateSorter(int id, SorterEntity sorter) {

        SorterEntity existingSorter =
                sorterRepository.findById(id).orElse(null);

        if (existingSorter == null) {
            return null;
        }

        existingSorter.setSorterName(sorter.getSorterName());
        existingSorter.setVendorName(sorter.getVendorName());
        existingSorter.setSortingType(sorter.getSortingType());
        existingSorter.setStatus(sorter.getStatus());

        return sorterRepository.save(existingSorter);
    }

    // Delete Sorter
    public boolean deleteSorter(int id) {

        if (!sorterRepository.existsById(id)) {
            return false;
        }

        sorterRepository.deleteById(id);
        return true;
    }
}
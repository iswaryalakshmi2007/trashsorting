package com.mrf.trashsorting.repository;

import com.mrf.trashsorting.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<ProductEntity, Integer> {

}
package com.mrf.trashsorting.controller;

import com.mrf.trashsorting.entity.ProductEntity;
import com.mrf.trashsorting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mrf/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(
            @RequestBody ProductEntity product) {

        ProductEntity savedProduct =
                productService.createProduct(product);

        return new ResponseEntity<>(
                savedProduct,
                HttpStatus.CREATED
        );
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {

        List<ProductEntity> products =
                productService.getAllProducts();

        return new ResponseEntity<>(
                products,
                HttpStatus.OK
        );
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(
            @PathVariable int id) {

        ProductEntity product =
                productService.getProductById(id);

        if (product == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                product,
                HttpStatus.OK
        );
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable int id,
            @RequestBody ProductEntity product) {

        ProductEntity updatedProduct =
                productService.updateProduct(id, product);

        if (updatedProduct == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                updatedProduct,
                HttpStatus.OK
        );
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable int id) {

        boolean deleted =
                productService.deleteProduct(id);

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
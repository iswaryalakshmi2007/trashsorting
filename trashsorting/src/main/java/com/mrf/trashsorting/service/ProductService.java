package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.ProductEntity;
import com.mrf.trashsorting.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create Product
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    // Get All Products
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product By ID
    public ProductEntity getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Update Product
    public ProductEntity updateProduct(
            int id,
            ProductEntity product) {

        ProductEntity existingProduct =
                productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setProductName(
                product.getProductName());

        existingProduct.setProductType(
                product.getProductType());

        existingProduct.setMaterialType(
                product.getMaterialType());

        existingProduct.setUnitPrice(
                product.getUnitPrice());

        return productRepository.save(existingProduct);
    }

    // Delete Product
    public boolean deleteProduct(int id) {

        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }
}
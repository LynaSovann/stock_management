package org.example.service;

import org.example.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllUnSavedProducts();
    void insertUnsavedProduct(Product product);
    void updateProduct(int id);
    void deleteProduct(int id);
}

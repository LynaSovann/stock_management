package org.example.service;

import org.example.model.Product;

import java.util.List;

public interface ProductService {
    void insertUnsavedProduct(Product product);
    List<Product> displayUnsavedProduct();
    void savedProduct();
    List<Product> displayProduct();
//    void insertSavedProduct(Product product);
    void updateProduct(int id);
    void deleteProduct(int id);
}

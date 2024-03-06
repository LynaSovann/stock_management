package org.example.service;

import org.example.model.Product;

import java.util.List;

public interface ProductService {
    void insertUnsavedProduct(Product product);
    List<Product> displayUnsavedProduct();
//    void savedProduct();
    List<Integer> savedProduct();
    List<Product> displayProduct();
    List<Product> displayUpdatedProduct();
//    void insertSavedProduct(Product product);
    void insertUpdateProduct(Product product);
    void deleteProduct(int id);
    boolean exitProgram();
}

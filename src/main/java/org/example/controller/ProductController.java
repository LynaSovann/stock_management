package org.example.controller;

import org.example.service.ProductService;
import org.example.service.serviceImpl.ProductServiceImpl;
import org.example.view.ProductView;

public class ProductController {
    ProductService productService = new ProductServiceImpl();
    ProductView productView = new ProductView();

    public void displayProducts() {
        productView.displayProducts(productService.getAllUnSavedProducts());
    }

    public void insertProduct() {
        productService.insertUnsavedProduct(productView.insertProduct());
    }

}

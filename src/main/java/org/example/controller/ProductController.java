package org.example.controller;

import org.example.service.ProductService;
import org.example.service.serviceImpl.ProductServiceImpl;
import org.example.view.ProductView;

public class ProductController {
    ProductService productService = new ProductServiceImpl();
    ProductView productView = new ProductView();

    public void insertUnsavedProduct() {
        productService.insertUnsavedProduct(productView.insertUnsavedProduct());
    }

    public void displayUnsavedProduct() {
        productView.displayUnsavedProduct(productService.displayUnsavedProduct());
    }

    public void savedProduct() {
        productService.savedProduct();
    }

    public void displayProduct() {
        productView.displayProduct(productService.displayProduct());
    }

//    public void insertProduct() {
//        productService.insertUnsavedProduct(productView.insertProduct());
//    }

}

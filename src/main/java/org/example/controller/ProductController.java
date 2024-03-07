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

    public void savedProduct(){
//        productView.displaySavedCase(productService.savedProduct(), productService.displayUpdatedProduct());
        productView.displaySavedCase(productService.savedProduct());
    }

    public void displayUnsavedProduct() {
        productView.displayUnsavedProduct(productService.displayUnsavedProduct(), productService.displayUpdatedProduct());
    }
    public void displayProduct() {
        productView.displayProduct(productService.displayProduct());
    }

    public void insertUpdateProduct() {
        productService.insertUpdateProduct(productView.insertUpdateProduct(productService.displayProduct()));
    }
    public void exitProgram() {
        productView.exitProgram(productService.exitProgram());
    }

}

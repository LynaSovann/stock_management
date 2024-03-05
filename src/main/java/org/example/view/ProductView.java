package org.example.view;

import org.example.model.Product;
import org.example.utils.validation.ValidationInput;

import java.sql.Timestamp;
import java.util.List;

public class ProductView {
    public void displayProducts(List<Product> products) {
        if(products.isEmpty()) {
            System.out.println("No data!");
        } else {
            products.forEach(product -> System.out.println(product.toString()));
        }
    }

    public Product insertProduct() {
        String name = ValidationInput.validate("=> Enter product name: ", "Invalid product name", "^[a-zA-Z]+");
        String unit_price = ValidationInput.validate("=> Enter unit price: ", "Invalid unit price", "^[0-9]+$");
        String qty = ValidationInput.validate("=> Enter product quantity: ", "invalid qty", "^[0-9]+");
        return new Product(0, name, Double.parseDouble(unit_price), Integer.parseInt(qty) , Timestamp.valueOf("2018-09-01 09:01:15"));
    }
}

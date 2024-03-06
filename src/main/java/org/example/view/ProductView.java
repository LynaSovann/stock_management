package org.example.view;

import org.example.model.Product;
import org.example.utils.table.RenderTable;
import org.example.utils.validation.ValidationInput;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductView {

    public Product insertUnsavedProduct() {
        System.out.println("Input information of product#" + Product.getCount());
        String name = ValidationInput.validate("=> Enter product name: ", "Invalid product name", "[a-zA-Z\\s]+[a-zA-Z]");
        String unit_price = ValidationInput.validate("=> Enter unit price: ", "Invalid unit price", "^[0-9]+[.]+[0-9]+");
        String qty = ValidationInput.validate("=> Enter product quantity: ", "invalid qty", "^[0-9]+");
        System.out.println( "\""+ name + "\"" + " has been added to unsaved table successfully.");
        return new Product(name, Double.parseDouble(unit_price), Integer.parseInt(qty));
    }

    public void displayUnsavedProduct(List<Product> products) {
        String choice;

        RenderTable.tableRender(RenderTable.header_title, products, "");
        do {
            System.out.println("I for Unsaved Insertion");
            System.out.println("U for Unsaved Update");
            System.out.println("B for back to main menu");

            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");

            switch (choice.toLowerCase()) {
                case "i":
                    RenderTable.tableRender(RenderTable.header_title, products, "");
                    break;
                case "u":
                    System.out.println("display updated product unsaved");
                    break;
                case "b":
                    return;
                default:
                    System.out.println("Invalid option! Please choose your option again mindfully!");
                    break;
            }
        } while(!choice.equalsIgnoreCase("e"));
    }


    public void displayProduct(List<Product> products) {
        if(products.isEmpty()) {
            System.out.println("No data!");
        } else {
            products.forEach(product -> System.out.println(product.toString()));
        }
    }
}

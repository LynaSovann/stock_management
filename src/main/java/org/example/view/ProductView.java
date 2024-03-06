package org.example.view;

import org.example.model.Product;
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

        if(products.isEmpty()) {
            System.out.println("No unsaved data!"); // display as empty data table
        } else {
            // display as table of unsaved product
            for(Product product : products) {
                System.out.println("Product#" + product.getId());
                System.out.println("Product Name: " + product.getName());
                System.out.println("Product unit price: " + product.getUnit_price());
                System.out.println("Product Qty: " + product.getQty());
                System.out.println("Imported date: " + product.getImported_date());
            }
        }
        do {
            System.out.println("I for Unsaved Insertion");
            System.out.println("U for Unsaved Update");
            System.out.println("B for back to main menu");

            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");

            switch (choice.toLowerCase()) {
                case "i":
                    if(products.isEmpty()) {
                        System.out.println("No unsaved data!"); // display as empty data table
                    } else {
                        // display as table of unsaved product
                        for(Product product : products) {
                            System.out.println("Product#" + product.getId());
                            System.out.println("Product Name: " + product.getName());
                            System.out.println("Product unit price: " + product.getUnit_price());
                            System.out.println("Product Qty: " + product.getQty());
                            System.out.println("Imported date: " + product.getImported_date());
                        }
                    }
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

    public void exitProgram(boolean exit) {
        if(exit) {
            System.out.println("==================================");
            System.out.println("\t* Goodbye");
            System.out.println("==================================");
        } else {
            System.out.println("Y/y to save and exit");
            System.out.println("N/n to exit and don't save");
            String yon = ValidationInput.validate("=> Enter your option: ", "Invalid option to exit", "[y\\sY\\n\\N]");
        }
    }
}

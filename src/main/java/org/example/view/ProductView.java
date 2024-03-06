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

    public void displayUnsavedProduct(List<Product> products, List<Product> updateProduct) {
        String choice;

        RenderTable.tableRender(RenderTable.header_title,"product list", products, "","");
        do {
            System.out.println("I for Unsaved Insertion");
            System.out.println("U for Unsaved Update");
            System.out.println("B for back to main menu");

            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");

            switch (choice.toLowerCase()) {
                case "i":
                    RenderTable.tableRender(RenderTable.header_title,"product list", products, "","");
                    break;
                case "u":
                    RenderTable.tableRender(RenderTable.header_title, updateProduct, "");
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
        RenderTable.tableRender(RenderTable.header_title, products, "");
    }

    public Product insertUpdateProduct() {
        String name = ValidationInput.validate("=> Enter new product name: ", "Invalid product name", "[a-zA-Z\\s]+[a-zA-Z]");
        String unit_price = ValidationInput.validate("=> Enter new unit price: ", "Invalid unit price", "^[0-9]+[.]+[0-9]+");
        String qty = ValidationInput.validate("=> Enter new product quantity: ", "invalid qty", "^[0-9]+");
        System.out.println( "\""+ name + "\"" + " has been added to unsaved updated table successfully.");
        return new Product(name, Double.parseDouble(unit_price), Integer.parseInt(qty));
    }

    public int findId() {
        String updateId = ValidationInput.validate("=> Enter product id to update: ", "Invalid id", "^[0-9]+");
        return Integer.parseInt(updateId);

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

package org.example.view;

import org.example.model.Product;
import org.example.utils.table.RenderTable;
import org.example.utils.validation.ValidationInput;

import java.time.LocalDate;
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
        } while(!choice.equalsIgnoreCase("b"));
    }


    public void displayProduct(List<Product> products) {
        RenderTable.tableRender(RenderTable.header_title, products, "");
    }

    public Product insertUpdateProduct( List<Product> productList) {
        String id = ValidationInput.validate("=> Enter product id to update: ", "Invalid id", "^[0-9]+");
        boolean found = false;
        for (Product product : productList) {
            if (product.getId() == Integer.parseInt(id)) {
                found = true;
                break;
            }
        }

        if(found) {
            String name = ValidationInput.validate("=> Enter new product name: ", "Invalid product name", "[a-zA-Z\\s]+[a-zA-Z]");
            String unit_price = ValidationInput.validate("=> Enter new unit price: ", "Invalid unit price", "^[0-9]+[.]+[0-9]+");
            String qty = ValidationInput.validate("=> Enter new product quantity: ", "invalid qty", "^[0-9]+");
            System.out.println( "\""+ name + "\"" + " has been added to unsaved updated table successfully.");
            return new Product(Integer.parseInt(id),name, Double.parseDouble(unit_price), Integer.parseInt(qty), LocalDate.now());
        } else {
            System.out.println("Id not found! failed to update!");
            return null;
        }

    }

    public void savedProduct(List<Product> products) {
        if(!products.isEmpty()) {
            for(Product pro : products) {
                System.out.println("* New product: "+  pro.getName() + " was inserted successfully *");
            }
        } else {
            System.out.println("Unsaved table is empty!");
        }
    }

    public void savedUpdateProduct(List<Product> products) {
        if(!products.isEmpty()) {
            for(Product pro : products) {
                System.out.println("* Product : "+  pro.getName() + " was updated successfully *");
            }
        } else {
            System.out.println("There's no updated product to save!");
        }
    }

    public void displaySavedCase(List<Product> products, List<Product> updateProducts) {
        String choice;

        do {
            System.out.println("Do you want to save Unsaved Inserted or Unsaved  Updated?");
            System.out.println("ui for saving Unsaved Insertion");
            System.out.println("uu for saving Unsaved Update");
            System.out.println("B for back to main menu");

            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");

            switch (choice.toLowerCase()) {
                case "ui":
                    savedProduct(products);
                    break;
                case "uu":
                    savedUpdateProduct(updateProducts);
                    break;
                case "b":
                    return;
                default:
                    System.out.println("Invalid option! Please choose your option again mindfully!");
                    break;
            }
        } while(!choice.equalsIgnoreCase("b"));
    }


    public void exitProgram(boolean exit, List<Product> savingProducts, List<Product> updatingProducts) {
        if(exit) {
            goodbyeTemplat();
        } else {
            System.out.println("You have unsaved product drafting in unsaved table. Do you want to save?");
            System.out.println("Y/y to save and exit");
            System.out.println("N/n to exit and don't save");
            String yon = ValidationInput.validate("=> Enter your option: ", "Invalid option to exit", "^[yYnN]$");
            if(yon.equals("Y") | yon.equals("y")) {
                if(!savingProducts.isEmpty()) {
                    savedProduct(savingProducts);
                }
                if(!updatingProducts.isEmpty()) {
                    savedUpdateProduct(updatingProducts);
                }
                goodbyeTemplat();
            } else {
                System.out.println("No saving...");
                goodbyeTemplat();
            }
        }
    }

    public static void goodbyeTemplat () {
        System.out.println("==================================");
        System.out.println("\t* Goodbye");
        System.out.println("==================================");
    }
}

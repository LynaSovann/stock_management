package org.example;

import org.example.controller.ProductController;
import org.example.utils.table.RenderTable;
import org.example.utils.validation.ValidationInput;

import java.util.Scanner;

public class StockManagement {
    public void run() {

        ProductController productController = new ProductController();

        String choice;
        do {
            System.out.println("\n\n");
            System.out.println("""
                             ███████╗████████╗ ██████╗  ██████╗██╗  ██╗    ███╗   ███╗ ██████╗ ███████╗ \s
                             ██╔════╝╚══██╔══╝██╔═══██╗██╔════╝██║ ██╔╝    ████╗ ████║██╔════╝ ██╔════╝ \s
                             ███████╗   ██║   ██║   ██║██║     █████╔╝     ██╔████╔██║██║  ███╗███████╗ \s
                             ╚════██║   ██║   ██║   ██║██║     ██╔═██╗     ██║╚██╔╝██║██║   ██║╚════██║ \s
                             ███████║   ██║   ╚██████╔╝╚██████╗██║  ██╗    ██║ ╚═╝ ██║╚██████╔╝███████║ \s
                             ╚══════╝   ╚═╝    ╚═════╝  ╚═════╝╚═╝  ╚═╝    ╚═╝     ╚═╝ ╚═════╝ ╚══════╝ \s
                             """);
            RenderTable.renderMenu();
            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");
            switch (choice.toLowerCase()) {
                case "*":
                    System.out.println("Display data");
                    productController.displayProducts();
                    break;
                case "w":
                    System.out.println("Write data");
                    productController.insertProduct();
                    break;
                case "r":
                    System.out.println("read data");
                    break;
                case "u":
                    System.out.println("update data");
                    break;
                case "d":
                    System.out.println("delete data");
                    break;
                case "e":
                    System.out.println("==================================");
                    System.out.println("\t* Goodbye");
                    System.out.println("==================================");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while(!choice.equalsIgnoreCase("e"));

    }
}
